package com.zjubiomedit.dao.record;

import com.zjubiomedit.domain.record.Evaluation;
import com.zjubiomedit.domain.record.GeneralRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
public interface EvaluationRepository extends CrudRepository<Evaluation, Long> {
    List<GeneralRecord> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

    List<Evaluation> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
