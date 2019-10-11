package com.zjubiomedit.dao.record;

import com.zjubiomedit.domain.record.GeneralRecord;
import com.zjubiomedit.domain.record.HAD;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
public interface HADRepository extends CrudRepository<HAD, Long> {
    List<GeneralRecord> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

    List<HAD> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
