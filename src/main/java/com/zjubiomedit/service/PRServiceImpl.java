package com.zjubiomedit.service;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.zjubiomedit.dao.patient.BodySignDao;
import com.zjubiomedit.dao.rehabilitation.*;
import com.zjubiomedit.domain.patient.BodySign;
import com.zjubiomedit.domain.rehabilitation.*;
import com.zjubiomedit.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PRServiceImpl implements PRService {

    //阈值设定
    private static final double BE2_COMPLETION = 0.8;
    private static final double BE_SP02 = 90;
    private static final int BE_hr = 110;
    private static final double WT_BORG = 6;
    private static final int WD_MET_LOW = 3;
    private static final int WD_MET_HIGH = 6;

    @Autowired
    private PRManagerRepository prManagerRepository;
    @Autowired
    private PRDataRepository prDataRepository;
    @Autowired
    private PRWarningRepository prWarningRepository;
    @Autowired
    private PRCompRepository prCompRepository;
    @Autowired
    private BodySignDao bodySignDao;


    @Override
    public String getPRManagerInfo(String patientId) {
        Optional<PRManager> managerInfo = prManagerRepository.findById(patientId);
        HashMap<String, Object> map = new HashMap<>();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if (managerInfo.isPresent()) {
            map.put("flag", Utils.SUCCESS);
            map.put("managerInfo", managerInfo.get());
        } else {
            map.put("flag", Utils.FAILURE);
            map.put("detail", "未查到康复档案");
        }
        return gson.toJson(map);
    }

    @Override
    public String updatePRManagerInfo(String patientId, String weekPlan) {
        Optional<PRManager> manager = prManagerRepository.findById(patientId);
        HashMap<String, Object> map = new HashMap<>();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        if (manager.isPresent()) {
            map.put("flag", Utils.SUCCESS);
            PRManager prManager = manager.get();
            prManager.setWeekPlan(weekPlan);
            PRManager save = prManagerRepository.save(prManager);
            map.put("managerInfo", save);
        } else {
            map.put("flag", Utils.FAILURE);
            map.put("detail", "未查到康复档案");
        }

        return gson.toJson(map);

    }

    @Override
    public String getDataRecordList(String patientId, String startTime, String endTime, String type) {
        HashMap<String, Object> map = new HashMap<>();
        List<PRData> dataList = new ArrayList<>();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start, end;
        try {
            start = sdf.parse(startTime);
            end = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            map.put("flag", Utils.FAILURE);
            map.put("detail", "时间解析错误");
            return gson.toJson(map);
        }
        if ("ALL".matches(type)) {
            dataList = prDataRepository.findByPatientIdentifierAndRecordTimeBetween(patientId, start, end);
            map.put("recordList", dataList);
        } else if ("SORT".matches(type)) {
            dataList = prDataRepository.findByPatientIdentifierAndRecordTimeBetween(patientId, start, end);
            HashMap<String,ArrayList<PRData>> groups = new HashMap<>();
            for (PRData prData : dataList){
                String key = prData.getExerciseId();
                groups.put(key,new ArrayList<>());
            }
            for (PRData prData : dataList){
                String key = prData.getExerciseId();
                groups.get(key).add(prData);
            }
            map.put("recordList", groups);
        }
          else {
            dataList = prDataRepository.findByPatientIdentifierAndExerciseIdAndRecordTimeBetween(patientId, type, start, end);
            map.put("recordList", dataList);
        }

        map.put("flag", Utils.SUCCESS);
        return gson.toJson(map);
    }

    @Override
    public String getLastDataRecordList(String patientId, String type, int num) {
        HashMap<String, Object> map = new HashMap<>();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        List<PRData> dataList = prDataRepository.findByPatientIdentifierAndExerciseIdOrderByRecordTime(patientId, type);
        int size = dataList.size();
        if (num <= size) {
            map.put("recordList", dataList.subList((size - num), size));
            map.put("flag", Utils.SUCCESS);
        } else {
            map.put("recordList", dataList);
            map.put("flag", Utils.SUCCESS);
        }
        return gson.toJson(map);
    }

    @Override
    public String saveDataRecord(String jsonString) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        PRData prData = gson.fromJson(jsonString, PRData.class);
        PRData save = prDataRepository.save(prData);
        if (save.getSeq() < 0){
            HashMap<String, Object> map = new HashMap<>();
            map.put("flag",Utils.FAILURE);
            map.put("detail","提交数据失败");
            return gson.toJson(map);
        }
        String result = detectPreWarning(save);
        return result;
    }

    private JsonObject transformJson(String jsonString) {
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(reader).getAsJsonObject();
        return object;
    }
    private JsonArray transformJson2Array(String jsonString) {
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        JsonParser jsonParser = new JsonParser();
        JsonArray object = jsonParser.parse(reader).getAsJsonArray();
        return object;
    }
    /**
     * 定时任务
     * 每天晚上23：00进行依从度计算和管理计划的更新
     * 如果是周六（一周最后一天）刷新一周计划weekplan
     */
//     private String weekPlanString = "[{\"id\":\"Sun\",\"schedule\":[\"WD1\"]},{\"id\":\"Mon\",\"schedule\":[\"BE2\",\"WD1\"]},{\"id\":\"Tue\",\"schedule\":[\"WT1\",\"WD1\"]},{\"id\":\"Wed\",\"schedule\":[\"BE2\",\"WD1\"]},{\"id\":\"Thu\",\"schedule\":[\"WT1\",\"WD1\"]},{\"id\":\"Fri\",\"schedule\":[\"BE2\",\"WD1\"]},{\"id\":\"Sat\",\"schedule\":[\"WT1\",\"WD1\"]}]";

    @Scheduled(cron = "0 0 23 * * *")
//    @Scheduled(cron = "0/10 * * * * *")
    public void reportCurrentTime() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        String newWeekPlan = createWeekPlan();

        Calendar today = Calendar.getInstance();
        int day = today.get(Calendar.DAY_OF_WEEK);
        Date record = today.getTime();
        Date dayStart = getDayStart(today);
        Date dayEnd = getDayEnd(today);
        Iterable<PRManager> allPatient = prManagerRepository.findAll();
        allPatient.forEach(prManager -> {
            String pid = prManager.getPatientIdentifier();
            JsonArray weekPlan = transformJson2Array(prManager.getWeekPlan());
            JsonArray schedule = weekPlan.get(day).getAsJsonObject().get("schedule").getAsJsonArray();
            List<PRData> patientData = prDataRepository.findByPatientIdentifierAndRecordTimeBetween(pid, dayStart, dayEnd);
            Optional<PRCompliance> compliance = prCompRepository.findTopByPatientIdentifierOrderByRecordTimeDesc(pid);
            if (schedule.size() > 0) {
                PRCompliance prc = new PRCompliance();
                int real, plan;
                String newComp;
                if (compliance.isPresent()) {
                    real = compliance.get().getRealtimes() + patientData.size();
                    plan = compliance.get().getPlantimes() + schedule.size();
                    Double value = (double) real / plan;
                    BigDecimal b = new BigDecimal(value);
                    newComp = b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                } else {
                    real = patientData.size();
                    plan = schedule.size();
                    Double value = (double) real / plan;
                    BigDecimal b = new BigDecimal(value);
                    newComp = b.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
                }
                prc.setCompliance(newComp);
                prc.setRealtimes(real);
                prc.setPlantimes(plan);
                prc.setPatientIdentifier(pid);
                prc.setRecordTime(record);
                prCompRepository.save(prc);
                System.out.println(prc.toString());
            }
            if (day >= 6 ){
                prManager.setWeekPlan(newWeekPlan);
                prManager.setDuration(prManager.getDuration()+1);
                prManagerRepository.save(prManager);
            }
        });
    }

    private Date getDayStart(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    private Date getDayEnd(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    private String createWeekPlan(){
        String[] weekName = {"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
        Gson gson = new GsonBuilder().create();
        ArrayList<HashMap> weekPlan = new ArrayList<>();
        for (int i = 0 ;i < 7 ; i++ ){
            HashMap<String,Object> dayPlan = new HashMap<>();
            ArrayList<String> schedule = new ArrayList<>();
            dayPlan.put("id",weekName[i]);
            if (i == 0) {
            } else if (i % 2 == 1){
                //1，3，5
//                schedule.add("BE2");
            } else {
                //2,4,6
                schedule.add("WT1");
            }
            schedule.add("BE2");
            schedule.add("WD1");
            dayPlan.put("schedule",schedule);
            weekPlan.add(dayPlan);
        }
        return gson.toJson(weekPlan);
    }
    /**
     * 呼吸操：时间duration 小于12分钟；血氧低于90；心率大于110；
     * 15分钟步行：Borg 高于6；时间duration不设预警；
     * 日志：计算运动当量3-6MET是合适范围，过低过高都设置提醒。
     *
     * @param prData
     * @return
     */
    private String detectPreWarning(PRData prData) {
        HashMap<String, Object> map = new HashMap<>();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        Long dataSeq = prData.getSeq();
        String patientId = prData.getPatientIdentifier();
        String content = prData.getContent();
        String exerciseId = prData.getExerciseId();
        JsonObject contentObj = transformJson(content);
        HashMap preWarning;
        if (dataSeq > 0) {
            //save data 成功，根据content检测是否达到预警阈值
            switch (exerciseId) {
                case "BE2":
                    preWarning = detectBreathE(contentObj);
                    break;
                case "WT1":
                    preWarning = detectWalkTest(contentObj);
                    break;
                case "WD1":
                    preWarning = detectWalkDairy(contentObj, patientId);
                    break;
                default:
                    preWarning = null;
                    break;
            }
            if (preWarning != null && preWarning.get("name") != "正常") {
                PRWarning prWarning = new PRWarning(patientId, new Date(), gson.toJson(preWarning), 0, dataSeq);
                prWarningRepository.save(prWarning);
            }
            preWarning.put("flag", Utils.SUCCESS);
            return gson.toJson(preWarning);
        } else {
            //save data 有错误
            map.put("flag", Utils.FAILURE);
            map.put("detail", "保存记录数据出错");
            return gson.toJson(map);
        }
    }

    private HashMap detectBreathE(JsonObject content) {
        HashMap<String, Object> map = new HashMap<>();
        String memo = content.get("memo").getAsString();
        boolean completion = content.get("completion").getAsDouble() < BE2_COMPLETION;
        boolean afterSPO2 = content.get("afterSPO2").getAsDouble() < BE_SP02;
        boolean afterHR = content.get("afterHR").getAsDouble() > BE_hr;
        if (memo == null || memo.isEmpty()) {
            if (afterSPO2 && afterHR) {
                map.put("id","BE03");
                map.put("name", "数据异常");
                map.put("reason", "运动后氧饱和度：" + content.get("afterSPO2").getAsString() + "% ；心率：" + content.get("afterHR").getAsString());
                map.put("message", "本次运动后血氧饱和度偏低，心率过快。\n 请尽量调整呼吸，注意休息。");
            } else if (afterSPO2) {
                map.put("id","BE04");
                map.put("name", "数据异常");
                map.put("reason", "运动后氧饱和度：" + content.get("afterSPO2").getAsString() + "%");
                map.put("message", "本次运动后血氧饱和度偏低。\n 请调整呼吸，注意休息。");
            } else if (afterHR) {
                map.put("id","BE05");
                map.put("name", "数据异常");
                map.put("reason", "运动后心率：" + content.get("afterHR").getAsString());
                map.put("message", "本次运动后心率过快。\n 请注意休息。");
            } else {
                if (completion) {
                    map.put("id","BE02");
                    map.put("name", "完成度异常");
                    map.put("reason", "完成度：" + content.get("completion").getAsString());
                    map.put("message", "本次运动计划尚未完成，下次要努力坚持哦！");
                } else {
                    map.put("id","BE00");
                    map.put("name", "正常");
                    map.put("reason", "完成度：" + content.get("completion").getAsString());
                    map.put("message", "今天呼吸操部分完成的很棒，明天也要加油哦！");
                }
            }
        } else {
            map.put("id","BE01");
            map.put("name", "主诉异常");
            map.put("reason", memo);
            map.put("message", "已将您的情况反馈给医生，请等待医生处理。");
        }

        return map;
    }

    private HashMap detectWalkTest(JsonObject content) {
        HashMap<String, Object> map = new HashMap<>();
        String memo = content.get("memo").getAsString();
        boolean borg = content.get("borg").getAsDouble() > WT_BORG;
        if (memo == null || memo.isEmpty()) {
            if (borg) {
                map.put("id","WT02");
                map.put("name", "数据异常");
                map.put("reason", "运动后Borg评分：" + content.get("borg").getAsString());
                map.put("message", "坚持就是胜利！步行运动后非常疲惫，要注意休息哦。");
            } else {
                map.put("id","WT00");
                map.put("name", "正常");
                map.put("reason", "运动后Borg评分：" + content.get("borg").getAsString());
                map.put("message", "本次步行运动状态不错哦，做得很好，加油！");
            }
        } else {
            map.put("id","WT01");
            map.put("name", "主诉异常");
            map.put("reason", memo);
            map.put("message", "已将您的情况反馈给医生，请等待医生处理。");
        }

        return map;
    }

    private HashMap detectWalkDairy(JsonObject content, String patientId) {
        HashMap<String, Object> map = new HashMap<>();
        String memo = content.get("memo").getAsString();
//        double duration = content.get("duration").getAsDouble();
//        double kCal = content.get("kCal").getAsDouble();
//        double km = content.get("km").getAsDouble();
        Double met = content.get("met").getAsDouble();
//        Double met = calculateMet(patientId, kCal, duration, km);
        boolean metLow = met < WD_MET_LOW;
        boolean metHigh = met > WD_MET_HIGH;
        if (memo == null || memo.isEmpty()) {
            if (metLow) {
                map.put("id","WD02");
                map.put("name", "数据异常");
                map.put("reason", "MET：" + met.toString());
                map.put("message", "今日运动量离目标还差那么一点点哦。\n你可以做的更好的，明天加油！");
            } else if (metHigh) {
                map.put("id","WD03");
                map.put("name", "数据异常");
                map.put("reason", "MET：" + met.toString());
                map.put("message", "今天的运动量好大哦，身体还耐受吗？\n 可以适当减小些运动量，这样对身体更有益，加油！");
            } else {
                map.put("id","WD00");
                map.put("name", "正常");
                map.put("reason", "MET：" + met.toString());
                map.put("message", "今天运动量达标，真棒！\n明天继续加油哦！");
            }
        } else {
            map.put("id","WD01");
            map.put("name", "主诉异常");
            map.put("reason", memo);
            map.put("message", "已将您的情况反馈给医生，请等待医生处理。");
        }

        return map;
    }

    private Double calculateMet(String patientId, double kCal, double duration, double km) {
        Optional<BodySign> weightItem = bodySignDao.findTopByPatientIdentifierAndBodySignItem(patientId, "体重");
        if (weightItem.isPresent()) {
            //有体重数据的计算公式
            Double weight = Double.parseDouble(weightItem.get().getBodySignValue());
            double value = kCal / weight / (duration / 60.0);
            BigDecimal b = new BigDecimal(value);
            return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } else {
            //无体重数据的估算
            double kmPerh = km / (duration / 60.0);
            double met = kmPerh * 3.5 / 4.8;
            BigDecimal b2 = new BigDecimal(met);
            return b2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        }
    }
}
