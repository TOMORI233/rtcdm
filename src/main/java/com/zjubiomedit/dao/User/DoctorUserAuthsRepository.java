package com.zjubiomedit.dao.User;

import com.zjubiomedit.dto.DoctorEndDto.DoctorListDto;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorUserAuthsRepository extends CrudRepository<DoctorUserAuths, Long> {

    Optional<DoctorUserAuths> findByUserName(String username);

    List<DoctorUserAuths> findByOrgCodeAndStatus(String orgCode, Integer status);

    @Query(value = "select " +
            "new com.zjubiomedit.dto.DoctorEndDto.DoctorListDto(dua2.userID, dua2.name) " +
            "from DoctorUserAuths dua1, DoctorUserAuths dua2 " +
            "where dua1.userID = :hospitalID " +
            "and dua1.auth = 1 " +
            "and dua2.auth = 0 " +
            "and dua2.orgCode = dua1.orgCode")
    List<DoctorListDto> findByHospitalId(@Param("hospitalID") Long hospitalID);

    @Query(value = "select auth from DoctorUserAuths where userID = :userID")
    Integer findAuthById(@Param("userID") Long userID);
}