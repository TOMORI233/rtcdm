package com.zjubiomedit.dao.Record;

import com.zjubiomedit.entity.Record.SMWTRecord;
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
public interface SMWTRecordRepository extends CrudRepository<SMWTRecord, Long> {
    List<SMWTRecord> findByPatientIDAndRecordTimeIsBetween(Long patientID, Date startDate, Date endDate);

    Page<SMWTRecord> findByPatientID(Long patientID, Pageable pageable);
}
