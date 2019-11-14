package com.zjubiomedit.service;

import com.zjubiomedit.dto.PatientEndDto.RecordCommit;
import com.zjubiomedit.util.Result;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface RecordService {
    /**
     *  新增数据记录
     */
    public Result createDataRecord(RecordCommit recordCommit);
}
