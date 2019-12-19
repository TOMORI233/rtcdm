package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.entity.Platform.ManagementApplicationReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface ManagementApplicationRepository extends JpaRepository<ManagementApplicationReview, Long> {
    Page<ManagementApplicationReview> findByDoctorID(Long doctorID, Pageable pageable);
}
