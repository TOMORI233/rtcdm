package com.zjubiomedit.dao.Dict;

import com.zjubiomedit.entity.Dict.OrgDict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface OrgDictRepository extends CrudRepository<OrgDict, Long> {
    List<OrgDict> findByDivisionCodeAndIsValid(String divisionCode, Integer isValid);

    List<OrgDict> findByParentOrgCodeAndIsValid(String parentOrgCode, Integer isValid);

    @Query(value = "select orgName from OrgDict " +
            "where orgCode = :orgCode and isValid = 1")
    String findOrgNameByOrgCode(String orgCode);

    @Query(value = "select od.orgName from OrgDict od " +
            "where od.orgCode in " +
            "(select orgCode from DoctorUserAuths " +
            "where userID = :doctorID)")
    String findOrgNameByDoctorID(Long doctorID);

    Optional<OrgDict> findByOrgCodeAndIsValid(String orgCode, Integer isValid);
}
