package com.zjubiomedit.dao.record;

import com.zjubiomedit.domain.record.GeneralRecord;
import com.zjubiomedit.domain.record.PHQ;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
public interface PHQRepository extends CrudRepository<PHQ, Long> {
    List<GeneralRecord> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

    List<PHQ> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
