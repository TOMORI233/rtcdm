package com.zjubiomedit.dao.record;

import com.zjubiomedit.domain.record.Discomfort;
import com.zjubiomedit.domain.record.GeneralRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/5/21
 */
public interface DiscomfortRepository extends CrudRepository<Discomfort, Long> {
    List<GeneralRecord> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

    List<Discomfort> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
