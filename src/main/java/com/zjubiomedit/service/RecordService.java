package com.zjubiomedit.service;

import com.zjubiomedit.dto.PatientEndDto.RecordCommitDto;
import com.zjubiomedit.util.Result;

import java.util.Date;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface RecordService {
    /**
     *  新增数据记录
     */
    public Result createDataRecord(RecordCommitDto recordCommitDto);

    Result fetchRecordList(Integer type, Long patientID, Date startDate, Date endDate);

    Result fetchRecordPage(Integer type, Long patientID, Date startDate, Date endDate, Integer pageIndex, Integer pageOffset);

    Result fetchLatestRecord(Integer type, Long patientID, Integer n);
}
