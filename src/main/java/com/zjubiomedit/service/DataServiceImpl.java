package com.zjubiomedit.service;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.zjubiomedit.dao.message.*;
import com.zjubiomedit.domain.message.*;
import com.zjubiomedit.dao.record.*;
import com.zjubiomedit.domain.record.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author yiiyuanliu
 * @Date 2018/4/10
 */
@Service
public class DataServiceImpl implements DataService {

	private static final int SUCCESS = 200;
	private static final int FAILURE = 255;
	private static final int UNKNOWN = 254;

	private static final int CAT = 1;
	private static final int GAD = 2;
	private static final int PHQ = 3;
	private static final int PEF = 4;
	private static final int Medication = 5;
	private static final int Discomfort = 6;
	private static final int Evaluation = 7;
	private static final int NewDiscomfort = 8;
	private static final int SixMWT = 9;
	private static final int HAD = 10;
	//message
	private static final int UNREAD = 0;
	private static final int READ = 1;
	private static final int FROMPATIENT = 0;
	private static final int FROMDOCTOR = 1;
	@Autowired
	private CATRepository catRepository;
	@Autowired
	private PHQRepository phqRepository;
	@Autowired
	private GADRepository gadRepository;
	@Autowired
	private PEFRepository pefRepository;
	@Autowired
	private EvaluationRepository evaluationRepository;
	@Autowired
	private SixMWTRepository sixMWTRepository;
	@Autowired
	private MedicineRepository medicineRepository;
	@Autowired
	private DiscomfortRepository discomfortRepository;
	@Autowired
	private NewDiscomfortRepository newDiscomfortRepository;
	@Autowired
	private PatientMessageRepository patientMessageRepository;
	@Autowired
	private HADRepository hadRepository;

	@Override
	public String commitData(String jsonString) {
		Map<String, Object> map = new HashMap<>();
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		try {
			JsonReader reader = new JsonReader(new StringReader(jsonString));
			JsonParser jsonParser = new JsonParser();
			JsonObject object = jsonParser.parse(reader).getAsJsonObject();
			String data = object.get("data").getAsString();
			String patientId = object.get("patientId").getAsString();
			int recordType = object.get("recordType").getAsInt();

			reader = new JsonReader(new StringReader(data));
			JsonObject jsonData = jsonParser.parse(reader).getAsJsonObject();
			String measureTime = jsonData.get("measureTime").getAsString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datetime;
			try {
				datetime = sdf.parse(measureTime);
			} catch (ParseException e) {
				e.printStackTrace();
				map.put("flag", FAILURE);
				return gson.toJson(map);
			}
			switch (recordType) {
				case CAT: {
					int score = jsonData.get("score").getAsInt();
					String answers = jsonData.get("answer").getAsString();
					int duration = jsonData.has("duration") ? jsonData.get("duration").getAsInt() : 0;
					com.zjubiomedit.domain.record.CAT cat = new CAT(patientId, datetime, score, answers, duration);
					CAT record = catRepository.save(cat);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);
				}
				case GAD: {
					int score = jsonData.get("score").getAsInt();
					String answers = jsonData.get("answer").getAsString();
					int duration = jsonData.has("duration") ? jsonData.get("duration").getAsInt() : 0;
					com.zjubiomedit.domain.record.GAD gad = new GAD(patientId, datetime, score, answers, duration);
					GAD record = gadRepository.save(gad);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);

				}
				case PHQ: {
					int score = jsonData.get("score").getAsInt();
					String answers = jsonData.get("answer").getAsString();
					int duration = jsonData.has("duration") ? jsonData.get("duration").getAsInt() : 0;
					com.zjubiomedit.domain.record.PHQ phq = new PHQ(patientId, datetime, score, answers, duration);
					com.zjubiomedit.domain.record.PHQ record = phqRepository.save(phq);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);

				}
				case HAD: {
					int score = jsonData.get("score").getAsInt();
					int had1 = jsonData.get("had1").getAsInt();
					int had2 = jsonData.get("had2").getAsInt();
					String answers = jsonData.get("answer").getAsString();
					int duration = jsonData.has("duration") ? jsonData.get("duration").getAsInt() : 0;
					com.zjubiomedit.domain.record.HAD had = new HAD(patientId, datetime, score, answers, duration, had1 ,had2);
					com.zjubiomedit.domain.record.HAD record = hadRepository.save(had);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);

				}
				case PEF: {
					int score = jsonData.get("value").getAsInt();
					com.zjubiomedit.domain.record.PEF pef = new PEF(patientId, datetime, score);
					com.zjubiomedit.domain.record.PEF record = pefRepository.save(pef);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);

				}
				case Medication: {
					String medicineName = jsonData.get("medicineName").getAsString();
					String dosage = (jsonData.has("dosage")) ? jsonData.get("dosage").getAsString() : null;
					String memo = (jsonData.has("memo")) ? jsonData.get("memo").getAsString() : null;
					Medicine medicine = new Medicine(patientId, medicineName, dosage, datetime, memo);
					Medicine record = medicineRepository.save(medicine);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);
				}
				case Discomfort: {
					int inflammation = jsonData.get("inflammation").getAsInt();
					int lung = jsonData.get("lung").getAsInt();
					int heart = jsonData.get("heart").getAsInt();
					int breath = jsonData.get("breath").getAsInt();
					String memo = (jsonData.has("memo")) ? jsonData.get("memo").getAsString() : null;
					Discomfort discomfort = new Discomfort(patientId, inflammation, lung, heart, breath, datetime, memo);
					Discomfort record = discomfortRepository.save(discomfort);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);
				}
				case NewDiscomfort: {
					int isDiscomfort = jsonData.get("isDiscomfort").getAsInt(); //0 正常 1 不适
					String symptom = (jsonData.has("symptom")) ? jsonData.get("symptom").getAsString() : null;
					NewDiscomfort discomfort = new NewDiscomfort(patientId,datetime,isDiscomfort,symptom);
					NewDiscomfort record = newDiscomfortRepository.save(discomfort);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);
				}
				case Evaluation: {
					int score = jsonData.get("value").getAsInt();
					com.zjubiomedit.domain.record.Evaluation evaluation = new Evaluation(patientId, datetime, score);
					com.zjubiomedit.domain.record.Evaluation record = evaluationRepository.save(evaluation);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);
				}
				case SixMWT: {
					int value = jsonData.get("value").getAsInt();
					String answers = jsonData.get("answers").getAsString();
					com.zjubiomedit.domain.record.SixMWT sixMWT = new SixMWT(patientId, datetime, value, answers);
					com.zjubiomedit.domain.record.SixMWT record = sixMWTRepository.save(sixMWT);
					map.put("flag", SUCCESS);
					map.put("recordID", record.getSeq());
					return gson.toJson(map);
				}
				default:
					break;
			}
			map.put("flag", UNKNOWN);
			return gson.toJson(map);
		} catch (NullPointerException e) {
			map.put("flag", FAILURE);
			return gson.toJson(map);
		}

	}

	@Override
	public String fetchData(String jsonString) {
		JsonReader reader = new JsonReader(new StringReader(jsonString));
		JsonParser jsonParser = new JsonParser();
		JsonObject object = jsonParser.parse(reader).getAsJsonObject();
		String start = object.get("start").getAsString();
		String end = object.get("end").getAsString();
		if (!start.contains(":")) {
			start += " 00:00:00";
		}
		if (!end.contains(":")) {
			end += " 23:59:59";
		}

		String patientId = object.get("patientId").getAsString();
		int recordType = object.get("recordType").getAsInt();
		HashMap<String, Object> map = new HashMap<>();
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate, endDate;
		try {
			startDate = sdf.parse(start);
			endDate = sdf.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
			map.put("flag", UNKNOWN);
			return gson.toJson(map);
		}
		switch (recordType) {
			case CAT: {
				List<CAT> recordList = catRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				map.put("flag", SUCCESS);
				map.put("recordList", recordList);
				return gson.toJson(map);
			}
			case GAD: {
				List<GAD> recordList = gadRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				map.put("flag", SUCCESS);
				map.put("recordList", recordList);
				return gson.toJson(map);
			}
			case PHQ: {
				List<PHQ> recordList = phqRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				map.put("flag", SUCCESS);
				map.put("recordList", recordList);
				return gson.toJson(map);
			}
			case HAD: {
				List<HAD> recordList = hadRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				map.put("flag", SUCCESS);
				map.put("recordList", recordList);
				return gson.toJson(map);
			}
			case PEF: {
				List<PEF> recordList = pefRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				map.put("flag", SUCCESS);
				map.put("recordList", recordList);
				return gson.toJson(map);
			}
			case Discomfort: {
				List<Discomfort> recordList = discomfortRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				map.put("flag", SUCCESS);
				map.put("recordList", recordList);
				return gson.toJson(map);
			}
			case NewDiscomfort: {
				List<NewDiscomfort> recordList = newDiscomfortRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				map.put("flag", SUCCESS);
				map.put("recordList", recordList);
				return gson.toJson(map);
			}
			case Medication: {
				List<Medicine> recordList = medicineRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				JsonElement element = gson.toJsonTree(recordList, new TypeToken<List<Medicine>>() {
				}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				map.put("flag", SUCCESS);
				map.put("recordList", jsonArray);
				return gson.toJson(map);
			}
			case Evaluation: {
				List<Evaluation> recordList = evaluationRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				map.put("flag", SUCCESS);
				map.put("recordList", recordList);
				return gson.toJson(map);
			}
			case SixMWT: {
				List<SixMWT> recordList = sixMWTRepository.findByPatientIdentifierAndMeasureTimeBetween(patientId, startDate, endDate);
				JsonElement element = gson.toJsonTree(recordList, new TypeToken<List<SixMWT>>() {
				}.getType());
				JsonArray jsonArray = element.getAsJsonArray();
				map.put("flag", SUCCESS);
				map.put("recordList", jsonArray);
				return gson.toJson(map);
			}

			default:
				break;
		}
		map.put("flag", UNKNOWN);
		return gson.toJson(map);
	}

	@Override
	public String latestData(String jsonString) {
		JsonReader reader = new JsonReader(new StringReader(jsonString));
		JsonParser jsonParser = new JsonParser();
		JsonObject object = jsonParser.parse(reader).getAsJsonObject();
		String patientId = object.get("patientId").getAsString();
		int recordType = object.get("recordType").getAsInt();
		int num = object.get("num").getAsInt();
		HashMap<String, Object> map = new HashMap<>();

		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		switch (recordType) {
			case CAT: {
				List<GeneralRecord> catList = catRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, catList);
			}
			case PHQ: {
				List<GeneralRecord> list = phqRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, list);
			}
			case GAD: {
				List<GeneralRecord> list = gadRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, list);
			}
			case HAD: {
				List<GeneralRecord> list = hadRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, list);
			}
			case PEF: {
				List<GeneralRecord> list = pefRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, list);
			}
			case Medication: {
				List<GeneralRecord> medicineList = medicineRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, medicineList);
			}
			case Evaluation: {
				List<GeneralRecord> list = evaluationRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, list);
			}
			case Discomfort: {
				List<GeneralRecord> discomfortList = discomfortRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, discomfortList);
			}
			case NewDiscomfort: {
				List<GeneralRecord> newDiscomfortList = newDiscomfortRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, newDiscomfortList);
			}
			case SixMWT: {
				List<GeneralRecord> list = sixMWTRepository.findByPatientIdentifierOrderByMeasureTimeDesc(patientId);
				return getLatestMap(num, map, gson, list);
			}
			default:
				break;
		}
		map.put("flag", UNKNOWN);
		return gson.toJson(map);
	}

	private String getLatestMap(int num, HashMap<String, Object> map, Gson gson, List<GeneralRecord> list) {
		if (num <= list.size()) {
			map.put("flag", SUCCESS);
			map.put("recordList", list.subList(0, num));
			return gson.toJson(map);
		} else {
			map.put("flag", FAILURE);
			return gson.toJson(map);
		}
	}

	@Override
	public String commitMessage(String jsonString) {
		Map<String, Object> map = new HashMap<>();
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		try {
			JsonReader reader = new JsonReader(new StringReader(jsonString));
			JsonParser jsonParser = new JsonParser();
			JsonObject object = jsonParser.parse(reader).getAsJsonObject();

			String patientId = object.get("patientId").getAsString();
			String doctor = object.get("doctor").getAsString();
			String message = object.get("message").getAsString();
			String messageTime = object.get("messageTime").getAsString();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date datetime;
			try {
				datetime = sdf.parse(messageTime);
			} catch (ParseException e) {
				e.printStackTrace();
				map.put("flag", FAILURE);
				return gson.toJson(map);
			}

			int mark = UNREAD;
			int from = FROMPATIENT;
			com.zjubiomedit.domain.message.PatientMessage msg = new PatientMessage(patientId, message, datetime, doctor, mark, from);
			PatientMessage record = patientMessageRepository.save(msg);
			map.put("flag", SUCCESS);
			map.put("recordID", record.getSeq());
			return gson.toJson(map);
//        }catch (ParseException e) {
//            map.put("flag", UNKNOWN);
//            return gson.toJson(map);
		} catch (NullPointerException e) {
			map.put("flag", FAILURE);
			return gson.toJson(map);
		}

	}

	@Override
	public String latestMessage(String jsonString) {
		HashMap<String, Object> map = new HashMap<>();
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		try {
			JsonReader reader = new JsonReader(new StringReader(jsonString));
			JsonParser jsonParser = new JsonParser();
			JsonObject object = jsonParser.parse(reader).getAsJsonObject();
			String patientId = object.get("patientId").getAsString();
			int num = object.get("messageNum").getAsInt();

			List<PatientMessage> patientMsgList = patientMessageRepository.findByPatientIdentifierOrderByMessageTime(patientId);

			map.put("flag", SUCCESS);
			int size = patientMsgList.size();
			if (num <= size) {
				map.put("recordList", patientMsgList.subList((size-num), size));
				return gson.toJson(map);
			} else {
//        		map.put("flag", FAILURE);
				map.put("recordList", patientMsgList);
				return gson.toJson(map);
			}
		} catch (NullPointerException e) {
			map.put("flag", FAILURE);
			return gson.toJson(map);
		}
	}

	@Override
	public String updateMessage(String jsonString){
		Map<String, Object> map = new HashMap<>();
		Gson gson = new GsonBuilder()
				.setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		try {
			JsonReader reader = new JsonReader(new StringReader(jsonString));
			JsonParser jsonParser = new JsonParser();
			JsonObject object = jsonParser.parse(reader).getAsJsonObject();

			String patientId = object.get("patientId").getAsString();
			String doctor = object.get("doctor").getAsString();
			List<PatientMessage> msg = patientMessageRepository.findByPatientIdentifierAndDoctorAndMarkAndMessageFrom(patientId,doctor, UNREAD, FROMDOCTOR);

			if (msg.size() == 0){
				map.put("flag", SUCCESS);
				map.put("recordSize", 0);
//				map.put("memo","no unread record");
				return gson.toJson(map);
			}
			for(PatientMessage item:msg){
				item.setMark(READ);
			}
			List<PatientMessage> record = (List) patientMessageRepository.saveAll(msg);
			System.out.println(record.get(0).getMessage());
			map.put("flag", SUCCESS);
			map.put("recordSize", record.size());
			return gson.toJson(map);
		} catch (NullPointerException e) {
			map.put("flag", FAILURE);
			return gson.toJson(map);
		}
	}
}
