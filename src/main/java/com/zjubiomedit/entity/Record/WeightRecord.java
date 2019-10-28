package com.zjubiomedit.entity.Record;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class WeightRecord extends RecordBaseEntity{
    @Column(nullable = false)
    private Float weight; // 单位: kg
}
