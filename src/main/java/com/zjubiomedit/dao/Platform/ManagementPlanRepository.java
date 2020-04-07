package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.entity.Platform.ManagementPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface ManagementPlanRepository extends JpaRepository<ManagementPlan, Long> {
    List<ManagementPlan> findByPatientID(Long patientID);
}
