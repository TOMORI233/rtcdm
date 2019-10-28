package com.zjubiomedit.entity.Record;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class DrugRecord extends RecordBaseEntity{
    @Column(length = 45)
    private String timePoint;
    @Column(nullable = false, length = 100)
    private String drugName;
    @Column(length = 45)
    private String dosage;
}
