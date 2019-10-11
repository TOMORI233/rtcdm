package com.zjubiomedit.dao.rehabilitation;

import com.zjubiomedit.domain.rehabilitation.PRData;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface PRDataRepository extends CrudRepository<PRData,Long> {

    List<PRData> findByPatientIdentifierAndRecordTimeBetween(String patientId, Date start, Date end);

    List<PRData> findByPatientIdentifierAndExerciseIdAndRecordTimeBetween(String patientId, String type, Date start, Date end);

    List<PRData> findByPatientIdentifierAndExerciseIdOrderByRecordTime(String patientId, String type);

}
