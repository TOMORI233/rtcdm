package com.zjubiomedit.entity.Record;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class PEFRecord extends RecordBaseEntity {
    private Integer value; // 峰流速值 单位 L/min
}
