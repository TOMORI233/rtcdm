package com.zjubiomedit.entity.Record;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Data
public class CATRecord extends RecordBaseEntity {

    @Column(nullable = false)
    private Integer score; // 问卷得分
    @Column(length = 100)
    private String answer; // 答案
    private Integer duration; // 回答问卷所用时间  单位：s

    @Transient
    private Integer value;
}
