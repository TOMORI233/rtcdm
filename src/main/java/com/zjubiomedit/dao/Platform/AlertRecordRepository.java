package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.dto.PagingDto.AlertDto.AlertBaseInfo;
import com.zjubiomedit.entity.Platform.AlertRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface AlertRecordRepository extends JpaRepository<AlertRecord, Long> {

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.AlertDto.AlertBaseInfo" +
            "(ar.serialNo, ar.patientID, ar.alertType, ar.alertName, ar.alertReason, ar.alertMessage, ar.alertTime, ar.status, ar.followupSerialNo, ar.ignoreReason, ar.executeDoctorID, ar.executeTime, " +
            "pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.manageStatus, mpi.manageStartDateTime, mpi.manageClass, mpi.complianceRate, mpi.followupTimes, mpi.lastFollowupDate, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from AlertRecord ar, PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where ar.status = :status " +
            "and ar.patientID in " +
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where mpi.manageStatus <> 9 and (mpi.doctorID = :viewerID or mpi.hospitalID = :viewerID))" +
            "and pub.userID = ar.patientID " +
            "and mpi.patientID = ar.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and dua.orgCode = od.orgCode " +
            "and cmd.patientID = ar.patientID " +
            "order by ar.alertTime desc")
    List<AlertBaseInfo> findAlertPageByViewerIDAndStatus(@Param("viewerID") Long viewerID, @Param("status") Integer status);

    @Query(value = "select count(distinct ar.patientID) " +
            "from AlertRecord ar " +
            "where ar.status = 0 " +
            "and ar.patientID in " +
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where mpi.manageStatus <> 9 and (mpi.doctorID = :viewerID or mpi.hospitalID = :viewerID))")
    Integer CountPatientByViewerID(@Param("viewerID") Long viewerID);

    Optional<AlertRecord> findBySerialNoAndStatus(Long serialNo, Integer status);

    Page<AlertRecord> findByPatientID(Long patientID, Pageable pageable);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.AlertDto.AlertBaseInfo" +
            "(ar.serialNo, ar.patientID, ar.alertType, ar.alertName, ar.alertReason, ar.alertMessage, ar.alertTime, ar.status, ar.followupSerialNo, ar.ignoreReason, ar.executeDoctorID, ar.executeTime, " +
            "pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.manageStatus, mpi.manageStartDateTime, mpi.manageClass, mpi.complianceRate, mpi.followupTimes, mpi.lastFollowupDate, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from AlertRecord ar, PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where ar.status = :status " +
            "and ar.patientID in " +
            "(select patientID from ReferralRecord " +
            "where status = 1 " +
            "and (doctorID = :viewerID or orgCode in" +
            "(select orgCode from DoctorUserAuths " +
            "where userID = :viewerID and auth = 1)))" +
            "and pub.userID = ar.patientID " +
            "and mpi.patientID = ar.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and dua.orgCode = od.orgCode " +
            "and cmd.patientID = ar.patientID " +
            "order by ar.alertTime desc")
    List<AlertBaseInfo> findReferralAlertPageByViewerIDAndStatus(Long viewerID, Integer status);

    @Query(value = "select count(distinct ar.patientID) " +
            "from AlertRecord ar " +
            "where ar.status = 0 " +
            "and ar.patientID in " +
            "(select patientID from ReferralRecord " +
            "where status = 1 " +
            "and (doctorID = :viewerID or orgCode in" +
            "(select orgCode from DoctorUserAuths " +
            "where userID = :viewerID and auth = 1)))")
    Integer CountReferralPatientByViewerID(Long viewerID);
}
