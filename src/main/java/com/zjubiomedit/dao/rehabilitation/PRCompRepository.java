package com.zjubiomedit.dao.rehabilitation;

import com.zjubiomedit.domain.rehabilitation.PRCompliance;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PRCompRepository extends CrudRepository<PRCompliance, Long> {
    Optional<PRCompliance> findTopByPatientIdentifierOrderByRecordTimeDesc(String patientId);
}
