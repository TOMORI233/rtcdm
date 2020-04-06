package com.zjubiomedit.dao.User;

import com.zjubiomedit.entity.User.PatientUserBaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientUserBaseInfoRepository extends JpaRepository<PatientUserBaseInfo, Long> {
    PatientUserBaseInfo findByUserID(Long userID);
}
