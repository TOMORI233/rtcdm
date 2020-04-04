package com.zjubiomedit.dao.User;

import com.zjubiomedit.entity.User.PatientUserAuths;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientUserAuthsRepository extends JpaRepository<PatientUserAuths, Long> {
    PatientUserAuths findByUserID(Long userID);
}
