package com.zjubiomedit.dao.patient;

import com.zjubiomedit.domain.patient.BodySign;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
public interface BodySignDao extends CrudRepository<BodySign, Long> {
    List<BodySign> findByPatientIdentifier(String patientId);

    Optional<BodySign> findTopByPatientIdentifierAndBodySignItem(String patientId, String item);
}
