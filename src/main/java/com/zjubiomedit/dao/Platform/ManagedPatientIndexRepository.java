package com.zjubiomedit.dao.Platform;

import com.zjubiomedit.dto.DoctorEndDto.PatientListDto;
import com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto;
import com.zjubiomedit.entity.Platform.ManagedPatientIndex;
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
public interface ManagedPatientIndexRepository extends JpaRepository<ManagedPatientIndex, Long> {

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto" +
            "(pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.patientID, mpi.manageStatus, mpi.complianceRate, mpi.manageStartDateTime, mpi.terminationDateTime, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where (mpi.patientID in " +
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where mpi.doctorID = :doctorID) " +
            "or mpi.patientID in " +
            "(select rr.patientID from ReferralRecord rr " +
            "where rr.doctorID = :doctorID and rr.status = 1)) " +
            "and cmd.patientID = mpi.patientID " +
            "and pub.userID = mpi.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and od.orgCode = dua.orgCode " +
            "order by mpi.patientID asc")
    Page<ManageIndexPagingDto> findAllManageIndexByDoctorID(@Param("doctorID") Long doctorID, Pageable pageable);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto" +
            "(pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.patientID, mpi.manageStatus, mpi.complianceRate, mpi.manageStartDateTime, mpi.terminationDateTime, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where mpi.doctorID = :doctorID " +
            "and mpi.manageStatus = 0 " +
            "and cmd.patientID = mpi.patientID " +
            "and pub.userID = mpi.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and od.orgCode = dua.orgCode " +
            "order by mpi.patientID asc")
    Page<ManageIndexPagingDto> findManagingManageIndexByDoctorID(Long doctorID, Pageable pageable);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto" +
            "(pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.patientID, mpi.manageStatus, mpi.complianceRate, mpi.manageStartDateTime, mpi.terminationDateTime, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where mpi.doctorID = :doctorID " +
            "and (mpi.manageStatus = 1 or mpi.manageStatus = 2) " +
            "and cmd.patientID = mpi.patientID " +
            "and pub.userID = mpi.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and od.orgCode = dua.orgCode " +
            "order by mpi.patientID asc")
    Page<ManageIndexPagingDto> findReferralOutManageIndexByDoctorID(Long doctorID, Pageable pageable);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto" +
            "(pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.patientID, mpi.manageStatus, mpi.complianceRate, mpi.manageStartDateTime, mpi.terminationDateTime, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where mpi.patientID in" +
            "(select rr.patientID from ReferralRecord rr " +
            "where rr.doctorID = :doctorID and rr.status = 1) " +
            "and cmd.patientID = mpi.patientID " +
            "and pub.userID = mpi.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and od.orgCode = dua.orgCode " +
            "order by mpi.patientID asc")
    Page<ManageIndexPagingDto> findReferralInManageIndexByDoctorID(Long doctorID, Pageable pageable);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto" +
            "(pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.patientID, mpi.manageStatus, mpi.complianceRate, mpi.manageStartDateTime, mpi.terminationDateTime, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where (mpi.patientID in" +
            "(select mpi.patientID from ManagedPatientIndex mpi, DoctorUserAuths dua " +
            "where mpi.doctorID = dua.userID and dua.orgCode = :orgCode)" +
            "or mpi.patientID in" +
            "(select rr.patientID from ReferralRecord rr " +
            "where rr.orgCode = :orgCode and rr.status = 1)) " +
            "and cmd.patientID = mpi.patientID " +
            "and pub.userID = mpi.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and od.orgCode = dua.orgCode " +
            "order by mpi.patientID asc")
    Page<ManageIndexPagingDto> findAllManageIndexByOrgCode(@Param("orgCode") String orgCode, Pageable pageable);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto" +
            "(pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.patientID, mpi.manageStatus, mpi.complianceRate, mpi.manageStartDateTime, mpi.terminationDateTime, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where mpi.patientID in" +
            "(select mpi.patientID from ManagedPatientIndex mpi, DoctorUserAuths dua " +
            "where mpi.doctorID = dua.userID and dua.orgCode = :orgCode) " +
            "and mpi.manageStatus = 0 " +
            "and cmd.patientID = mpi.patientID " +
            "and pub.userID = mpi.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and od.orgCode = dua.orgCode " +
            "order by mpi.patientID asc")
    Page<ManageIndexPagingDto> findManagingManageIndexByOrgCode(String orgCode, Pageable pageable);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto" +
            "(pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.patientID, mpi.manageStatus, mpi.complianceRate, mpi.manageStartDateTime, mpi.terminationDateTime, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd " +
            "where mpi.patientID in" +
            "(select mpi.patientID from ManagedPatientIndex mpi, DoctorUserAuths dua " +
            "where mpi.doctorID = dua.userID and dua.orgCode = :orgCode) " +
            "and (mpi.manageStatus = 1 or mpi.manageStatus = 2) " +
            "and cmd.patientID = mpi.patientID " +
            "and pub.userID = mpi.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and od.orgCode = dua.orgCode " +
            "order by mpi.patientID asc")
    Page<ManageIndexPagingDto> findReferralOutManageIndexByOrgCode(String orgCode, Pageable pageable);

    @Query(value = "select new com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto" +
            "(pub.name, pub.sex, pub.dateOfBirth, " +
            "mpi.patientID, mpi.manageStatus, mpi.complianceRate, mpi.manageStartDateTime, mpi.terminationDateTime, " +
            "dua.name, dua.orgCode, " +
            "od.orgName, " +
            "cmd.manageLevel) " +
            "from PatientUserBaseInfo pub, ManagedPatientIndex mpi, DoctorUserAuths dua, OrgDict od, COPDManageDetail cmd, ReferralRecord rr " +
            "where rr.patientID in" +
            "(select rr.patientID from ReferralRecord rr " +
            "where rr.orgCode = :orgCode and rr.status = 1) " +
            "and mpi.patientID = rr.patientID " +
            "and cmd.patientID = rr.patientID " +
            "and pub.userID = rr.patientID " +
            "and dua.userID = mpi.doctorID " +
            "and od.orgCode = dua.orgCode " +
            "order by mpi.patientID asc")
    Page<ManageIndexPagingDto> findReferralInManageIndexByOrgCode(String orgCode, Pageable pageable);

    @Query(value = "select count(distinct patientID) " +
            "from ManagedPatientIndex " +
            "where doctorID = :doctorID and manageStatus = 0")
    Long CountManagingByDoctorID(@Param("doctorID") Long doctorID);

    @Query(value = "select count(distinct patientID) " +
            "from ManagedPatientIndex " +
            "where doctorID = :doctorID and (manageStatus = 1 or manageStatus = 2)")
    Long CountReferralOutByDoctorID(@Param("doctorID") Long doctorID);

    @Query(value = "select count(distinct patientID) " +
            "from ReferralRecord " +
            "where doctorID = :doctorID and status = 1")
    Long CountReferralInByDoctorID(@Param("doctorID") Long doctorID);

    @Query(value = "select count(distinct mpi.patientID) " +
            "from ManagedPatientIndex mpi, DoctorUserAuths dua " +
            "where mpi.doctorID = dua.userID " +
            "and mpi.manageStatus = 0 " +
            "and dua.orgCode = :orgCode")
    Long CountManagingByOrgCode(@Param("orgCode") String orgCode);

    @Query(value = "select count(distinct mpi.patientID) " +
            "from ManagedPatientIndex mpi, DoctorUserAuths dua " +
            "where mpi.doctorID = dua.userID " +
            "and (mpi.manageStatus = 1 or mpi.manageStatus = 2) " +
            "and dua.orgCode = :orgCode")
    Long CountReferralOutByOrgCode(@Param("orgCode") String orgCode);

    @Query(value = "select count(distinct rr.patientID) " +
            "from ReferralRecord rr " +
            "where rr.orgCode = :orgCode " +
            "and rr.status = 1")
    Long CountReferralInByOrgCode(@Param("orgCode") String orgCode);

    @Query(value = "select new com.zjubiomedit.dto.DoctorEndDto.PatientListDto" +
            "(pub.userID, pub.name) " +
            "from PatientUserBaseInfo pub " +
            "where (:patientType = 0 and (pub.userID in " + //全部
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where mpi.doctorID = :viewerID or mpi.hospitalID = :viewerID) " +
            "or pub.userID in " +
            "(select rr.patientID from ReferralRecord rr " +
            "where rr.status = 1 " +
            "and (rr.doctorID = :viewerID or rr.orgCode in" +
            "(select dua.orgCode from DoctorUserAuths dua " +
            "where dua.userID = :viewerID and dua.auth = 1)))))" +

            "or (:patientType = 1 and pub.userID in " + //管理中
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where mpi.manageStatus = 0 " +
            "and (mpi.doctorID = :viewerID or mpi.hospitalID = :viewerID))) " +

            "or (:patientType = 2 and pub.userID in " + //转出
            "(select mpi.patientID from ManagedPatientIndex mpi " +
            "where (mpi.manageStatus = 1 or mpi.manageStatus = 2) " +
            "and (mpi.doctorID = :viewerID or mpi.hospitalID = :viewerID))) " +

            "or (:patientType = 3 and pub.userID in " + //转入
            "(select rr.patientID from ReferralRecord rr " +
            "where rr.status = 1 " +
            "and (rr.doctorID = :viewerID or rr.orgCode in" +
            "(select dua.orgCode from DoctorUserAuths dua " +
            "where dua.userID = :viewerID and dua.auth = 1))))")
    List<PatientListDto> findPatientByViewerIDAndType(@Param("viewerID") Long viewerID, @Param("patientType") Integer patientType);

    Optional<ManagedPatientIndex> findByPatientID(Long patientID);
}
