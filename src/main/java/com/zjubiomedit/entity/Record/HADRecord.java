package com.zjubiomedit.entity.Record;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Data
public class HADRecord extends RecordBaseEntity {

    @Column(nullable = false)
    private Integer score; // 问卷得分
    @Column(length = 100)
    private String answer; // 答案
    private Integer duration; // 回答问卷所用时间  单位：s
    @Column(nullable = false)
    private Integer anxiety; // 焦虑部分问卷得分
    @Column(nullable = false)
    private Integer depression; // 抑郁部分问卷得分
}
