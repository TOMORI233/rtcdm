package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.dto.PagingDto.AlertPagingDto;
import com.zjubiomedit.entity.Platform.AlertRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface AlertRecordRepository extends JpaRepository<AlertRecord, Long> {

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.AlertPagingDto" +
            "(ar.serialNo, ar.patientID, ar.alertType, ar.alertName, ar.alertReason, ar.alertMessage, ar.alertTime, ar.status, ar.followUpSerialNo, ar.ignoreReason, ar.executeDoctorID, ar.executeTime, " +
            "pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.manageStatus, mpi.complianceRate, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from AlertRecord ar, PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where ar.status = 0 " +
            "and ar.patientID in " +
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where mpi.doctorID = :viewerID or mpi.hospitalID = :viewerID)" +
            "and pub.userID = ar.patientID " +
            "and mpi.patientID = ar.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and dua.orgCode = od.orgCode " +
            "and cmd.patientID = ar.patientID")
    Page<AlertPagingDto> findAlertPageByViewerID(@Param("viewerID") Long viewerID, Pageable pageable);

    @Query(value = "select count(distinct ar.patientID) " +
            "from AlertRecord ar " +
            "where ar.status = 0 " +
            "and ar.patientID in " +
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where mpi.doctorID = :viewerID or mpi.hospitalID = :viewerID)")
    Integer CountPatientByViewerID(@Param("viewerID") Long viewerID);

    AlertRecord findBySerialNo(Long serialNo);

    Page<AlertRecord> findByPatientID(Long patientID, Pageable pageable);
}
