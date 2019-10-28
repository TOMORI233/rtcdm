package com.zjubiomedit.entity.Record;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class DiscomfortRecord extends RecordBaseEntity {
    @Column(length = 100)
    private String symptom;
    @Column(nullable = false)
    private Integer isDiscomfort; // 0—正常 1—不适

}
