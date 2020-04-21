package com.zjubiomedit.dao.Record;

import com.zjubiomedit.entity.Record.DiscomfortRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface DiscomfortRecordRepository extends CrudRepository<DiscomfortRecord, Long> {
    List<DiscomfortRecord> findByPatientIDAndRecordTimeIsBetween(Long patientID, Date startDate, Date endDate);

    Page<DiscomfortRecord> findByPatientID(Long patientID, Pageable pageable);

    Optional<DiscomfortRecord> findFirstByPatientIDAndStatusAndIsDiscomfortOrderByRecordTimeDesc(Long patientID, Integer status, Integer isDiscomfort);
}
