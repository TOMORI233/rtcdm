package com.zjubiomedit.dao.message;

import com.zjubiomedit.domain.message.PatientMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author leiyiSheng
 * @date 2019/4/9
 */
public interface PatientMessageRepository extends CrudRepository<PatientMessage, Long> {
 	List<PatientMessage> findByPatientIdentifierOrderByMessageTime(String patientId);
 	List<PatientMessage> findByPatientIdentifierAndDoctorAndMarkAndMessageFrom(String patientId, String doctor,int mark, int messageFrom);
}
