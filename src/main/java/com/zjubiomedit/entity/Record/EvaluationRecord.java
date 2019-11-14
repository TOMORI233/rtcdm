package com.zjubiomedit.entity.Record;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class EvaluationRecord extends RecordBaseEntity {
    private Integer value; // 综合评估得分
}
