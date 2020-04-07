package com.zjubiomedit.dao.Record;

import com.zjubiomedit.entity.Record.CATRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface CATRecordRepository extends CrudRepository<CATRecord, Long> {

    List<CATRecord> findByPatientIDAndRecordTimeIsBetween(Long patientID, Date startDate, Date endDate);

    Page<CATRecord> findByPatientID(Long patientID, Pageable pageable);
}
