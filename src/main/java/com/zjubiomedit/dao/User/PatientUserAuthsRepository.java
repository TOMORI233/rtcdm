package com.zjubiomedit.dao.User;

import com.zjubiomedit.entity.User.PatientUserAuths;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PatientUserAuthsRepository extends JpaRepository<PatientUserAuths, Long> {
    Optional<PatientUserAuths> findByUserID(Long userID);

    @Query("select userID from PatientUserAuths " +
            "where status = 0")
    List<Long> findAllActivePatientID();

    @Query("select count(userID) from PatientUserAuths")
    Long CountAll();

    Optional<PatientUserAuths> findByUserName(String userName);
}
