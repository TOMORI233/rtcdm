package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.dto.PagingDto.FollowupPagingDto;
import com.zjubiomedit.entity.Platform.FollowupPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface FollowupPlanRepository extends JpaRepository<FollowupPlan, Long> {

    @Query(value = "select count(distinct fp.patientID) from FollowupPlan fp " +
            "where fp.patientID in " +
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where mpi.doctorID = :viewerID or mpi.hospitalID = :viewerID) " +
            "and fp.planDate > :startDate " +
            "and fp.planDate < :endDate " +
            "and fp.status = :status")
    Long CountByViewerIDAndStatusAndDate(Long viewerID, Integer status, Date startDate, Date endDate);

    Optional<FollowupPlan> findBySerialNoAndStatus(Long serialNo, Integer status);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.FollowupPagingDto" +
            "(fp.serialNo, fp.patientID, fp.planDate, fp.status, fp.followUpType, fp.memo, " +
            "pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.followupTimes, mpi.lastFollowupDate, mpi.manageStatus, mpi.complianceRate, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from FollowupPlan fp, PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where fp.patientID in " +
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where mpi.doctorID = :viewerID or mpi.hospitalID = :viewerID) " +
            "and fp.status = :status " +
            "and fp.planDate > :startDate and fp.planDate < :endDate " +
            "and pub.userID = fp.patientID " +
            "and mpi.patientID = fp.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and dua.orgCode = od.orgCode " +
            "and cmd.patientID = fp.patientID")
    Page<FollowupPagingDto> findFollowupPageByViewerIDAndStatusAndTime(Long viewerID, Integer status, Date startDate, Date endDate, Pageable pageable);

    @Query(value = "select count(distinct fp.patientID) from FollowupPlan fp " +
            "where fp.patientID in " +
            "(select patientID from ReferralRecord " +
            "where doctorID = :viewerID or orgCode in" +
            "(select orgCode from DoctorUserAuths " +
            "where userID = :viewerID and auth = 1)) " +
            "and fp.planDate > :startDate " +
            "and fp.planDate < :endDate " +
            "and fp.status = :status")
    Long CountReferralByViewerIDAndStatusAndDate(Long viewerID, Integer status, Date startDate, Date endDate);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.FollowupPagingDto" +
            "(fp.serialNo, fp.patientID, fp.planDate, fp.status, fp.followUpType, fp.memo, " +
            "pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.followupTimes, mpi.lastFollowupDate, mpi.manageStatus, mpi.complianceRate, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from FollowupPlan fp, PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where fp.patientID in " +
            "(select patientID from ReferralRecord " +
            "where doctorID = :viewerID or orgCode in" +
            "(select orgCode from DoctorUserAuths " +
            "where userID = :viewerID and auth = 1)) " +
            "and fp.status = :status " +
            "and fp.planDate > :startDate and fp.planDate < :endDate " +
            "and pub.userID = fp.patientID " +
            "and mpi.patientID = fp.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and dua.orgCode = od.orgCode " +
            "and cmd.patientID = fp.patientID")
    Page<FollowupPagingDto> findReferralFollowupPageByViewerIDAndStatusAndTime(Long viewerID, Integer status, Date startDate, Date endDate, Pageable pageable);
}
