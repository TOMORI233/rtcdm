package com.zjubiomedit.dao.User;

import com.zjubiomedit.entity.User.PatientUserAuths;
import org.springframework.data.repository.CrudRepository;

public interface PatientUserAuthsRepository extends CrudRepository<PatientUserAuths, Long> {
}
