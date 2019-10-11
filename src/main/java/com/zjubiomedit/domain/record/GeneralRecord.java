package com.zjubiomedit.domain.record;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/6
 */
@MappedSuperclass
public abstract class GeneralRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;
    @Column(columnDefinition = "nvarchar")
    private String patientIdentifier;
    private Date measureTime;

    GeneralRecord(){}

    GeneralRecord(String patientIdentifier, Date measureTime) {
        this.patientIdentifier = patientIdentifier;
        this.measureTime = measureTime;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getMeasureTime() {
        return measureTime;
    }

    public long getSeq() {
        return seq;
    }

    public void setMeasureTime(Date measureTime) {
        this.measureTime = measureTime;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }
}
