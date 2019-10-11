package com.zjubiomedit.dao.record;

import com.zjubiomedit.domain.record.GeneralRecord;
import com.zjubiomedit.domain.record.SixMWT;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
public interface SixMWTRepository extends CrudRepository<SixMWT, Long> {
    List<GeneralRecord> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

    List<SixMWT> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
