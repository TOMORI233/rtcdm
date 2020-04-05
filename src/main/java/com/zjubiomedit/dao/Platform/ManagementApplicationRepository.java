package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.dto.PagingDto.RegisterPagingDto;
import com.zjubiomedit.entity.Platform.ManagementApplicationReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface ManagementApplicationRepository extends JpaRepository<ManagementApplicationReview, Long> {
    Optional<ManagementApplicationReview> findBySerialNo(Long serialNo);

    @Query(value = "select " +
            "new com.zjubiomedit.dto.PagingDto.RegisterPagingDto" +
            "(pub.name, pub.sex, pub.dateOfBirth, pub.profession, pub.education, pub.height, pub.weight, pua.registerDateTime, mar.serialNo, mar.doctorID, mar.diagnosis) " +
            "from PatientUserBaseInfo pub, PatientUserAuths pua, ManagementApplicationReview mar " +
            "where (mar.doctorID = :viewerID or mar.hospitalID = :viewerID) " +
            "and mar.status = 0 " +
            "and pua.status = 10 " +
            "and mar.patientID = pua.userID " +
            "and mar.patientID = pub.userID")
    Page<RegisterPagingDto> findByViewerID(@Param("viewerID") Long viewerID, Pageable pageable);
}
