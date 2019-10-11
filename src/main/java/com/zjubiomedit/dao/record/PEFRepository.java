package com.zjubiomedit.dao.record;

import com.zjubiomedit.domain.record.GeneralRecord;
import com.zjubiomedit.domain.record.PEF;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
public interface PEFRepository extends CrudRepository<PEF, Long> {
    List<GeneralRecord> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

    List<PEF> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
