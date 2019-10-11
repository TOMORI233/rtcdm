package com.zjubiomedit.dao.record;

import com.zjubiomedit.domain.record.GAD;
import com.zjubiomedit.domain.record.GeneralRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
public interface GADRepository extends CrudRepository<GAD, Long> {
    List<GeneralRecord> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

    List<GAD> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
