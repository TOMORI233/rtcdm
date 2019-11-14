package com.zjubiomedit.entity.Record;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class SMWTRecord extends RecordBaseEntity {
    private Integer value; // 六分钟步行测试距离
    private String answer; //borg
}
