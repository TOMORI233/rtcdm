package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.dto.PagingDto.ReferralReviewPagingDto;
import com.zjubiomedit.entity.Platform.ReferralRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReferralRecordRepository extends JpaRepository<ReferralRecord, Long> {

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ReferralReviewPagingDto" +
            "(rr.serialNo, rr.patientID, rr.referralType, rr.referralPurpose, rr.referralReason, rr.doctorID, rr.startDateTime, " +
            "pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.manageStatus, mpi.complianceRate, " +
            "duat.name, dual.name, dual.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from ReferralRecord rr, PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dual, DoctorUserAuths duat, OrgDict od, COPDManageDetail cmd " +
            "where rr.status = 0 " +
            "and rr.orgCode in " +
            "(select orgCode from DoctorUserAuths " +
            "where userID = :hospitalID)" +
            "and pub.userID = rr.patientID " +
            "and mpi.patientID = rr.patientID " +
            "and duat.userID = rr.doctorID " +
            "and dual.userID = mpi.doctorID " +
            "and dual.orgCode = od.orgCode " +
            "and cmd.patientID = rr.patientID")
    Page<ReferralReviewPagingDto> findReferralReviewPageByHospitalID(Long hospitalID, Pageable pageable);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ReferralReviewPagingDto" +
            "(rr.serialNo, rr.patientID, rr.referralType, rr.referralPurpose, rr.referralReason, rr.doctorID, rr.startDateTime, " +
            "pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.manageStatus, mpi.complianceRate, " +
            "duat.name, dual.name, dual.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from ReferralRecord rr, PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dual, DoctorUserAuths duat, OrgDict od, COPDManageDetail cmd " +
            "where rr.status = 0 " +
            "and rr.doctorID = :doctorID " +
            "and pub.userID = rr.patientID " +
            "and mpi.patientID = rr.patientID " +
            "and duat.userID = rr.doctorID " +
            "and dual.userID = mpi.doctorID " +
            "and dual.orgCode = od.orgCode " +
            "and cmd.patientID = rr.patientID")
    Page<ReferralReviewPagingDto> findReferralReviewPageByDoctorID(Long doctorID, Pageable pageable);

    ReferralRecord findBySerialNo(Long serialNo);
}