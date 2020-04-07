package com.zjubiomedit.dao.Record;

import com.zjubiomedit.entity.Record.WeightRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface WeightRecordRepository extends CrudRepository<WeightRecord, Long> {
    List<WeightRecord> findByPatientIDAndRecordTimeIsBetween(Long patientID, Date startDate, Date endDate);

    Page<WeightRecord> findByPatientID(Long patientID, Pageable pageable);
}
