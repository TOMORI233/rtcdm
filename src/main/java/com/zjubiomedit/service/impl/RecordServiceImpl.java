package com.zjubiomedit.service.impl;

import com.google.gson.Gson;
import com.zjubiomedit.config.exception.CommonJsonException;
import com.zjubiomedit.dao.Record.*;
import com.zjubiomedit.dto.PatientEndDto.RecordCommitDto;
import com.zjubiomedit.entity.Record.*;
import com.zjubiomedit.service.RecordService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
@Service
public class RecordServiceImpl implements RecordService {
    private CATRecordRepository catRecordRepository;
    private DiscomfortRecordRepository discomfortRecordRepository;
    private DrugRecordRepository drugRecordRepository;
    private EvaluationRecordRepository evaluationRecordRepository;
    private HADRecordRepository hadRecordRepository;
    private PEFRecordRepository pefRecordRepository;
    private WeightRecordRepository weightRecordRepository;
    private SMWTRecordRepository smwtRecordRepository;


    @Autowired
    public void setCatRecordRepository(CATRecordRepository catRecordRepository) {
        this.catRecordRepository = catRecordRepository;
    }

    @Autowired
    public void setDiscomfortRecordRepository(DiscomfortRecordRepository discomfortRecordRepository) {
        this.discomfortRecordRepository = discomfortRecordRepository;
    }

    @Autowired
    public void setDrugRecordRepository(DrugRecordRepository drugRecordRepository) {
        this.drugRecordRepository = drugRecordRepository;
    }

    @Autowired
    public void setEvaluationRecordRepository(EvaluationRecordRepository evaluationRecordRepository) {
        this.evaluationRecordRepository = evaluationRecordRepository;
    }

    @Autowired
    public void setHadRecordRepository(HADRecordRepository hadRecordRepository) {
        this.hadRecordRepository = hadRecordRepository;
    }

    @Autowired
    public void setPefRecordRepository(PEFRecordRepository pefRecordRepository) {
        this.pefRecordRepository = pefRecordRepository;
    }

    @Autowired
    public void setWeightRecordRepository(WeightRecordRepository weightRecordRepository) {
        this.weightRecordRepository = weightRecordRepository;
    }

    @Autowired
    public void setSmwtRecordRepository(SMWTRecordRepository smwtRecordRepository) {
        this.smwtRecordRepository = smwtRecordRepository;
    }

    @Override
    public Result createDataRecord(RecordCommitDto recordCommitDto) {
        try {
            int type = recordCommitDto.getType();
            Gson gson = new Gson();
            switch (type) {
                case Utils.CAT:
                    CATRecord catRecord = gson.fromJson(recordCommitDto.getData(), CATRecord.class);
                    CATRecord catSave = catRecordRepository.save(catRecord);
                    return new Result(catSave);
                case Utils.DISCOMFORT:
                    DiscomfortRecord discomfortRecord = gson.fromJson(recordCommitDto.getData(), DiscomfortRecord.class);
                    DiscomfortRecord discomfortSave = discomfortRecordRepository.save(discomfortRecord);
                    return new Result(discomfortSave);
                case Utils.DRUG:
                    DrugRecord drugRecord = gson.fromJson(recordCommitDto.getData(), DrugRecord.class);
                    DrugRecord drugSave = drugRecordRepository.save(drugRecord);
                    return new Result(drugSave);
                case Utils.HAD:
                    HADRecord hadRecord = gson.fromJson(recordCommitDto.getData(), HADRecord.class);
                    HADRecord hadSave = hadRecordRepository.save(hadRecord);
                    return new Result(hadSave);
                case Utils.PEF:
                    PEFRecord pefRecord = gson.fromJson(recordCommitDto.getData(), PEFRecord.class);
                    PEFRecord pefSave = pefRecordRepository.save(pefRecord);
                    return new Result(pefSave);
                case Utils.WEIGHT:
                    WeightRecord weightRecord = gson.fromJson(recordCommitDto.getData(), WeightRecord.class);
                    WeightRecord weightSave = weightRecordRepository.save(weightRecord);
                    return new Result(weightSave);
                case Utils.SMWT:
                    SMWTRecord smwtRecord = gson.fromJson(recordCommitDto.getData(), SMWTRecord.class);
                    SMWTRecord smwtSave = smwtRecordRepository.save(smwtRecord);
                    return new Result(smwtSave);
                default:
                    return new Result(ErrorEnum.E_10006);
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result fetchRecordList(Integer type, Long patientID, Date startDate, Date endDate) {
        try {
            switch (type) {
                case Utils.CAT:
                    List<CATRecord> catRecordList = catRecordRepository.findByPatientIDAndRecordTimeIsBetween(patientID, startDate, endDate);
                    return new Result(catRecordList);
                case Utils.DISCOMFORT:
                    List<DiscomfortRecord> discomfortRecordList = discomfortRecordRepository.findByPatientIDAndRecordTimeIsBetween(patientID, startDate, endDate);
                    return new Result(discomfortRecordList);
                case Utils.DRUG:
                    List<DrugRecord> drugRecordList = drugRecordRepository.findByPatientIDAndRecordTimeIsBetween(patientID, startDate, endDate);
                    return new Result(drugRecordList);
                case Utils.HAD:
                    List<HADRecord> hadRecordList = hadRecordRepository.findByPatientIDAndRecordTimeIsBetween(patientID, startDate, endDate);
                    return new Result(hadRecordList);
                case Utils.PEF:
                    List<PEFRecord> pefRecordList = pefRecordRepository.findByPatientIDAndRecordTimeIsBetween(patientID, startDate, endDate);
                    return new Result(pefRecordList);
                case Utils.WEIGHT:
                    List<WeightRecord> weightRecordList = weightRecordRepository.findByPatientIDAndRecordTimeIsBetween(patientID, startDate, endDate);
                    return new Result(weightRecordList);
                case Utils.SMWT:
                    List<SMWTRecord> smwtRecordList = smwtRecordRepository.findByPatientIDAndRecordTimeIsBetween(patientID, startDate, endDate);
                    return new Result(smwtRecordList);
                default:
                    return new Result(ErrorEnum.E_10006);
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result fetchRecordPage(Integer type, Long patientID, Date startDate, Date endDate, Integer pageIndex, Integer pageOffset) {
        return null;
    }

    @Override
    public Result fetchLatestRecord(Integer type, Long patientID, Integer n) {
        try {
            // 通过page的方式取前n条数据
            Pageable pageable = PageRequest.of(0, n, Sort.Direction.DESC, "recordTime");
            switch (type) {
                case Utils.CAT:
                    Page<CATRecord> catRecordList = catRecordRepository.findByPatientID(patientID, pageable);
                    return new Result(catRecordList);
                case Utils.DISCOMFORT:
                    Page<DiscomfortRecord> discomfortRecordList = discomfortRecordRepository.findByPatientID(patientID, pageable);
                    return new Result(discomfortRecordList);
                case Utils.DRUG:
                    Page<DrugRecord> drugRecordList = drugRecordRepository.findByPatientID(patientID, pageable);
                    return new Result(drugRecordList);
                case Utils.HAD:
                    Page<HADRecord> hadRecordList = hadRecordRepository.findByPatientID(patientID, pageable);
                    return new Result(hadRecordList);
                case Utils.PEF:
                    Page<PEFRecord> pefRecordList = pefRecordRepository.findByPatientID(patientID, pageable);
                    return new Result(pefRecordList);
                case Utils.WEIGHT:
                    Page<WeightRecord> weightRecordList = weightRecordRepository.findByPatientID(patientID, pageable);
                    return new Result(weightRecordList);
                case Utils.SMWT:
                    Page<SMWTRecord> smwtRecordList = smwtRecordRepository.findByPatientID(patientID, pageable);
                    return new Result(smwtRecordList);
                default:
                    return new Result(ErrorEnum.E_10006);
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }
}
