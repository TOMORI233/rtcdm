package com.zjubiomedit.dao.Record;

import com.zjubiomedit.entity.Record.EvaluationRecord;
import com.zjubiomedit.entity.Record.PEFRecord;
import org.springframework.data.repository.CrudRepository;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface EvaluationRecordRepository extends CrudRepository<EvaluationRecord, Long> {
}
