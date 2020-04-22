package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.entity.Platform.COPDManageDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface COPDManageDetailRepository extends JpaRepository<COPDManageDetail, Long> {
    Optional<COPDManageDetail> findByPatientID(Long patientID);
}
