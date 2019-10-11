package com.zjubiomedit.dao.record;


import com.zjubiomedit.domain.record.GeneralRecord;
import com.zjubiomedit.domain.record.NewDiscomfort;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

/**
 * @Author leiyi sheng
 * @Date 2019/5/
 */

public interface NewDiscomfortRepository extends CrudRepository<NewDiscomfort,Long> {
	List<GeneralRecord> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

	List<NewDiscomfort> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
