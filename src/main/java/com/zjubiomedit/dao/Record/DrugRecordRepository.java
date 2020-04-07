package com.zjubiomedit.dao.Record;

import com.zjubiomedit.entity.Record.DrugRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-02
 */
public interface DrugRecordRepository extends JpaRepository<DrugRecord, Long> {

    List<DrugRecord> findByPatientIDAndRecordTimeIsBetween(Long patientID, Date startDate, Date endDate);

    Page<DrugRecord> findByPatientID(Long patientID, Pageable pageable);
}
