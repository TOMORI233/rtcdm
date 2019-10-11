package com.zjubiomedit.dao.patient;

import com.zjubiomedit.domain.patient.PatientLogin;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
public interface PatientLoginDao extends CrudRepository<PatientLogin, Long> {
    Optional<PatientLogin> findByPatientIdentifier(String patientId);
}
