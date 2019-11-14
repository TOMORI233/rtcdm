package com.zjubiomedit.dao.Dict;

import com.zjubiomedit.entity.Dict.DivisionDict;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DivisionDictRepository extends CrudRepository <DivisionDict, Long>{

    List<DivisionDict> findByIsValid(Integer isValid);
}
