package com.zjubiomedit.dao.record;

import com.zjubiomedit.domain.record.GeneralRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author yiiyuanliu
 * @Date 2018/5/21
 */
@NoRepositoryBean
public interface GeneralRepository<T extends GeneralRecord, ID extends Serializable> extends CrudRepository<T, ID> {
    List<T> findByPatientIdentifierOrderByMeasureTimeDesc(String patientId);

    List<T> findByPatientIdentifierAndMeasureTimeBetween(String patientId, Date start, Date end);
}
