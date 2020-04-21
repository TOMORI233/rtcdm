package com.zjubiomedit.service.impl;

import com.zjubiomedit.config.exception.CommonJsonException;
import com.zjubiomedit.dao.Platform.AlertRecordRepository;
import com.zjubiomedit.dao.Platform.ManagementApplicationRepository;
import com.zjubiomedit.dao.Record.*;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserBaseInfoRepository;
import com.zjubiomedit.entity.Platform.AlertRecord;
import com.zjubiomedit.entity.Platform.ManagementApplicationReview;
import com.zjubiomedit.entity.Record.*;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.entity.User.PatientUserAuths;
import com.zjubiomedit.entity.User.PatientUserBaseInfo;
import com.zjubiomedit.service.AutoService;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.AlertEnum;
import com.zjubiomedit.util.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AutoServiceImpl implements AutoService {

    @Autowired
    DoctorUserAuthsRepository doctorUserAuthsRepository;
    @Autowired
    PatientUserAuthsRepository patientUserAuthsRepository;
    @Autowired
    PatientUserBaseInfoRepository patientUserBaseInfoRepository;
    @Autowired
    CATRecordRepository catRecordRepository;
    @Autowired
    PEFRecordRepository pefRecordRepository;
    @Autowired
    HADRecordRepository hadRecordRepository;
    @Autowired
    DiscomfortRecordRepository discomfortRecordRepository;
    @Autowired
    AlertRecordRepository alertRecordRepository;
    @Autowired
    EvaluationRecordRepository evaluationRecordRepository;
    @Autowired
    ManagementApplicationRepository managementApplicationRepository;

    @Override
    public void autoInsertDocUser(Long count) {
        log.info("autoInsertDocUser " + count.toString());
        DoctorUserAuths newDoc = new DoctorUserAuths();
        newDoc.setUserName("doc" + count.toString());
        newDoc.setAuth(0);
        newDoc.setName("医生" + count.toString());
        newDoc.setOrgCode("A1");
        newDoc.setPassword("1");
        doctorUserAuthsRepository.save(newDoc);
        log.info(newDoc.toString());
    }

    private void insertAlert(Long patientID, String alertCode, String alertType, String alertName, String alertMsg, String alertReason) {
        try {
            AlertRecord alertRecord = new AlertRecord();
            alertRecord.setPatientID(patientID);
            alertRecord.setAlertCode(alertCode);
            alertRecord.setAlertType(alertType);
            alertRecord.setAlertName(alertName);
            alertRecord.setAlertMessage(alertMsg);
            alertRecord.setAlertReason(alertReason);
            alertRecord.setStatus(Utils.ALERT_UNPROCESSED);
            alertRecordRepository.save(alertRecord);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }

    }

    private Double calFEV1Value(Long patientID) {
        Double fev1Value = null;
        Optional<PatientUserBaseInfo> baseInfoOptional = patientUserBaseInfoRepository.findByUserID(patientID);
        PatientUserBaseInfo patientUserBaseInfo;
        if (baseInfoOptional.isPresent()) {
            patientUserBaseInfo = baseInfoOptional.get();
        } else {
            return fev1Value;
        }
        Integer height = patientUserBaseInfo.getHeight();
        int age = 0;
        try {
            age = Utils.getAgeByBirth(patientUserBaseInfo.getDateOfBirth());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (patientUserBaseInfo.getSex().equals(Utils.MAN)) {
            fev1Value = 75.6 + 20.4 * age - 0.41 * age * age + 0.002 * age * age * age + 1.19 * height;
        } else if (patientUserBaseInfo.getSex().equals(Utils.WOMAN)) {
            fev1Value = 282.0 + 1.79 * age - 0.046 * age * age + 0.68 * height;
        }
        return fev1Value;
    }

    @Override
    public void autoGenerateWeeklyAlert() {
        List<Long> patientIDList = patientUserAuthsRepository.findAllActivePatientID();
        for (Long patientID : patientIDList) {
            //计算FEV1
            Double fev1Value = calFEV1Value(patientID);
            if (fev1Value == null) {
                continue;
            }
            //周单项预警：CAT预警、PEF预警、心理预警
            //CAT预警，轻度11~20，中度21~30，重度>30
            Optional<CATRecord> catRecordOptional = catRecordRepository.findFirstByPatientIDAndStatusOrderByRecordTimeDesc(patientID, Utils.RECORD_UNWARNED);
            catRecordOptional.ifPresent(catRecord -> {
                Integer catScore = catRecord.getScore();
                boolean catIsWarned = true;
                if (catScore > 10 && catScore <= 20) {
                    insertAlert(patientID, AlertEnum.CODE_CAT, AlertEnum.TYPE_CAT, AlertEnum.NAME_CAT_1, AlertEnum.MSG_CAT_1, AlertEnum.REASON_LAST);
                } else if (catScore > 20 && catScore <= 30) {
                    insertAlert(patientID, AlertEnum.CODE_CAT, AlertEnum.TYPE_CAT, AlertEnum.NAME_CAT_2, AlertEnum.MSG_CAT_2, AlertEnum.REASON_LAST);
                } else if (catScore > 30) {
                    insertAlert(patientID, AlertEnum.CODE_CAT, AlertEnum.TYPE_CAT, AlertEnum.NAME_CAT_3, AlertEnum.MSG_CAT_3, AlertEnum.REASON_LAST);
                } else {
                    catIsWarned = false;
                }
                if (catIsWarned) {
                    catRecord.setStatus(Utils.RECORD_WARNED);
                    catRecordRepository.save(catRecord);
                }
            });

            //PEF预警，重度<60%预计值，轻度60%~80%预计值
            Optional<PEFRecord> pefRecordOptional = pefRecordRepository.findFirstByPatientIDAndStatusOrderByRecordTimeDesc(patientID, Utils.RECORD_UNWARNED);
            pefRecordOptional.ifPresent(pefRecord -> {
                Integer pefValue = pefRecord.getValue();
                boolean pefIsWarned = true;
                if (pefValue >= 0.6 * fev1Value && pefValue <= 0.8 * fev1Value) {
                    insertAlert(patientID, AlertEnum.CODE_PEF, AlertEnum.TYPE_PEF, AlertEnum.NAME_PEF_1, AlertEnum.MSG_PEF_1, AlertEnum.REASON_LAST);
                } else if (pefValue < 0.6 * fev1Value) {
                    insertAlert(patientID, AlertEnum.CODE_PEF, AlertEnum.TYPE_PEF, AlertEnum.NAME_PEF_2, AlertEnum.MSG_PEF_2, AlertEnum.REASON_LAST);
                } else {
                    pefIsWarned = false;
                }
                if (pefIsWarned) {
                    pefRecord.setStatus(Utils.RECORD_WARNED);
                    pefRecordRepository.save(pefRecord);
                }
            });

            //心理预警：焦虑预警、抑郁预警
            Optional<HADRecord> hadRecordOptional = hadRecordRepository.findFirstByPatientIDAndStatusOrderByRecordTimeDesc(patientID, Utils.RECORD_UNWARNED);
            hadRecordOptional.ifPresent(hadRecord -> {
                Integer anxiety = hadRecord.getAnxiety();
                Integer depression = hadRecord.getDepression();
                boolean anxIsWarned = true;
                boolean depIsWarned = true;
                //焦虑预警，轻度8-10，中度11-14，重度15-21
                if (anxiety >= 8 && anxiety <= 10) {
                    insertAlert(patientID, AlertEnum.CODE_ANX, AlertEnum.TYPE_HAD, AlertEnum.NAME_ANX_1, AlertEnum.MSG_ANX_1, AlertEnum.REASON_LAST);
                } else if (anxiety >= 11 && anxiety <= 14) {
                    insertAlert(patientID, AlertEnum.CODE_ANX, AlertEnum.TYPE_HAD, AlertEnum.NAME_ANX_2, AlertEnum.MSG_ANX_2, AlertEnum.REASON_LAST);
                } else if (anxiety >= 15) {
                    insertAlert(patientID, AlertEnum.CODE_ANX, AlertEnum.TYPE_HAD, AlertEnum.NAME_ANX_3, AlertEnum.MSG_ANX_3, AlertEnum.REASON_LAST);
                } else {
                    anxIsWarned = false;
                }
                //抑郁预警，轻度8-10，中度11-14，重度15-21
                if (depression >= 8 && depression <= 10) {
                    insertAlert(patientID, AlertEnum.CODE_DEP, AlertEnum.TYPE_HAD, AlertEnum.NAME_DEP_1, AlertEnum.MSG_DEP_1, AlertEnum.REASON_LAST);
                } else if (depression >= 11 && depression <= 14) {
                    insertAlert(patientID, AlertEnum.CODE_DEP, AlertEnum.TYPE_HAD, AlertEnum.NAME_DEP_2, AlertEnum.MSG_DEP_2, AlertEnum.REASON_LAST);
                } else if (depression >= 15) {
                    insertAlert(patientID, AlertEnum.CODE_DEP, AlertEnum.TYPE_HAD, AlertEnum.NAME_DEP_3, AlertEnum.MSG_DEP_3, AlertEnum.REASON_LAST);

                } else {
                    depIsWarned = false;
                }
                if (anxIsWarned || depIsWarned) {
                    hadRecord.setStatus(Utils.RECORD_WARNED);
                    hadRecordRepository.save(hadRecord);
                }
            });

            //周综合预警：转诊预警
            if (catRecordOptional.isPresent() && pefRecordOptional.isPresent() && hadRecordOptional.isPresent()) {
                CATRecord catRecord = catRecordOptional.get();
                Integer catScore = catRecord.getScore();
                PEFRecord pefRecord = pefRecordOptional.get();
                Integer pefValue = pefRecord.getValue();
                HADRecord hadRecord = hadRecordOptional.get();
                Integer anxiety = hadRecord.getAnxiety();
                Integer depression = hadRecord.getDepression();
                boolean refIsWarned = true;
                if (catScore >= 11 && pefValue >= 0.6 * fev1Value && pefValue <= 0.8 * fev1Value && anxiety >= 11 && depression >= 11) {
                    //一级预警：CAT>=11,PEF 60%~80%预计值,Anxiety>=11,Depression>=11 达到条件提示转诊
                    insertAlert(patientID, AlertEnum.CODE_REF, AlertEnum.TYPE_REF, AlertEnum.NAME_REF_1, AlertEnum.MSG_REF, AlertEnum.REASON_LAST);
                } else if (catScore >= 11 && pefValue <= 0.6 * fev1Value && anxiety >= 15 && depression >= 15) {
                    //二级预警：CAT>=30,PEF <=60%预计值,Anxiety>=15,Depression>=15
                    insertAlert(patientID, AlertEnum.CODE_REF, AlertEnum.TYPE_REF, AlertEnum.NAME_REF_2, AlertEnum.MSG_REF, AlertEnum.REASON_LAST);
                } else {
                    refIsWarned = false;
                }
                if (refIsWarned) {
                    catRecord.setStatus(Utils.RECORD_WARNED);
                    catRecordRepository.save(catRecord);
                    pefRecord.setStatus(Utils.RECORD_WARNED);
                    pefRecordRepository.save(pefRecord);
                    hadRecord.setStatus(Utils.RECORD_WARNED);
                    hadRecordRepository.save(hadRecord);
                }
            }
        }
    }

    @Override
    public void autoGenerateCompliance() {
        //complianceRate = [N(CAT)+N(PEF)+N(HAD)]/N(医嘱规定)

    }

    @Override
    public void autoGenerateDailyAlert() {
        //日单项预警：不适预警
        List<Long> patientIDList = patientUserAuthsRepository.findAllActivePatientID();
        for (Long patientID : patientIDList) {
            Optional<DiscomfortRecord> discomfortRecord = discomfortRecordRepository.findFirstByPatientIDAndStatusAndIsDiscomfortOrderByRecordTimeDesc(patientID, Utils.RECORD_UNWARNED, Utils.IS_DISCOMFORT);
            discomfortRecord.ifPresent(record -> {
                insertAlert(patientID, AlertEnum.CODE_DIS, AlertEnum.TYPE_DIS, AlertEnum.NAME_DIS, AlertEnum.MSG_DIS, "不适症状：" + record.getSymptom());
                record.setStatus(Utils.RECORD_WARNED);
                discomfortRecordRepository.save(record);
            });
        }
    }

    @Override
    public void autoGenerateManagePlan() {

    }

    @Override
    public void autoGenerateEvaluation() {
        List<Long> patientIDList = patientUserAuthsRepository.findAllActivePatientID();
        for (Long patientID : patientIDList) {
            Double fev1Value = calFEV1Value(patientID);
            Optional<CATRecord> catRecordOptional = catRecordRepository.findFirstByPatientIDOrderByRecordTimeDesc(patientID);
            Optional<PEFRecord> pefRecordOptional = pefRecordRepository.findFirstByPatientIDOrderByRecordTimeDesc(patientID);
            catRecordOptional.ifPresent(catRecord -> pefRecordOptional.ifPresent(pefRecord -> {
                Integer catScore = catRecord.getScore();
                Integer pefValue = pefRecord.getValue();
                int resultValue;
                EvaluationRecord evaluationRecord = new EvaluationRecord();
                evaluationRecord.setPatientID(patientID);
                evaluationRecord.setRecordTime(new Date());
                if (pefValue >= 0.8 * fev1Value) {
                    if (catScore <= 10) {
                        resultValue = 80;
                    } else if (catScore <= 20) {
                        resultValue = 60;
                    } else if (catScore <= 30) {
                        resultValue = 40;
                    } else {
                        resultValue = 20;
                    }
                } else if (pefValue >= 0.6 * fev1Value && pefValue < 0.8 * fev1Value) {
                    if (catScore <= 10) {
                        resultValue = 60;
                    } else if (catScore <= 20) {
                        resultValue = 60;
                    } else if (catScore <= 30) {
                        resultValue = 40;
                    } else {
                        resultValue = 20;
                    }
                } else {
                    resultValue = 20;
                }
                evaluationRecord.setValue(resultValue);
                evaluationRecordRepository.save(evaluationRecord);
            }));
        }
    }

    @Override
    public void autoInsertPatUser(Long count) {
        log.info("autoInsertPatUser " + count.toString());
        PatientUserAuths patientUserAuths = new PatientUserAuths();
        PatientUserBaseInfo patientUserBaseInfo = new PatientUserBaseInfo();
        ManagementApplicationReview managementApplicationReview = new ManagementApplicationReview();
        patientUserAuths.setUserName("pat" + count.toString());
        patientUserAuths.setPassword("1");
        patientUserAuths.setStatus(10);
        patientUserBaseInfo.setUserID(count);
        Calendar calendar = Calendar.getInstance();
        calendar.set(count.intValue() + 1950, Calendar.JANUARY, 1);
        Date date = calendar.getTime();
        patientUserBaseInfo.setDateOfBirth(date);
        patientUserBaseInfo.setName("患者" + count.toString());
        patientUserBaseInfo.setWeight(60.0f + count.floatValue());
        patientUserBaseInfo.setHeight(170);
        if (count % 2 == 0) {
            patientUserBaseInfo.setSex(Utils.WOMAN);
        } else {
            patientUserBaseInfo.setSex(Utils.MAN);
        }
        managementApplicationReview.setPatientID(count);
        managementApplicationReview.setHospitalID(1L);
//        managementApplicationReview.setDoctorID();
        patientUserAuthsRepository.save(patientUserAuths);
        patientUserBaseInfoRepository.save(patientUserBaseInfo);
        managementApplicationRepository.save(managementApplicationReview);
        log.info(patientUserBaseInfo.toString());
    }

    @Override
    public Long getDocCount() {
        return doctorUserAuthsRepository.CountAll() + 1;
    }

    @Override
    public Long getPatCount() {
        return patientUserAuthsRepository.CountAll() + 1;
    }
}
