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
import org.springframework.stereotype.Service;

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
}
