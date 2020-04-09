package com.zjubiomedit.dao.Dict;

import com.zjubiomedit.entity.Dict.OrgDict;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface OrgDictRepository extends CrudRepository<OrgDict, Long> {
    List<OrgDict> findByDivisionCodeAndIsValid(String divisionCode, Integer isValid);

    List<OrgDict> findByParentOrgCodeAndIsValid(String parentOrgCode, Integer isValid);

    @Query(value = "select orgName from OrgDict " +
            "where orgCode = :orgCode and isValid = :isValid")
    String findOrgNameByOrgCodeAndIsValid(String orgCode, Integer isValid);
}
