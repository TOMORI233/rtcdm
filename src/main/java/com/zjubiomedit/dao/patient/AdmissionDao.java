package com.zjubiomedit.dao.patient;

import com.zjubiomedit.domain.patient.Admission;
import org.springframework.data.repository.CrudRepository;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/12
 */
public interface AdmissionDao extends CrudRepository<Admission, String> {
}
