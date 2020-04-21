package com.zjubiomedit.dao.User;

import com.zjubiomedit.entity.User.PatientUserBaseInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientUserBaseInfoRepository extends JpaRepository<PatientUserBaseInfo, Long> {
    Optional<PatientUserBaseInfo> findByUserID(Long userID);
}
