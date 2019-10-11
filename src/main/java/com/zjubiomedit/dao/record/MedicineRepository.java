package com.zjubiomedit.dao.record;

import com.zjubiomedit.domain.record.GeneralRecord;
import com.zjubiomedit.domain.record.Medicine;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/5/18
 */
public interface MedicineRepository extends CrudRepository<Medicine, Long> {
    List<GeneralRecord> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

    List<Medicine> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
