package com.zjubiomedit.domain.patient;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
@Entity(name = "BodySigns")
public class BodySign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String patientIdentifier;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String bodySignItem;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String units;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String bodySignValue;
    @Column(nullable = false)
    private Date timePoint;
    @Column(nullable = false)
    private Date recordTime;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String encounterIdentifier;

    public BodySign(String patientIdentifier, String bodySignItem, String unit, String bodySignValue, Date timePoint, Date recordTime) {
        this.patientIdentifier = patientIdentifier;
        this.bodySignItem = bodySignItem;
        this.units = unit;
        this.bodySignValue = bodySignValue;
        this.timePoint = timePoint;
        this.recordTime = recordTime;
        this.encounterIdentifier = "1";
    }

    BodySign(){}

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public long getSeq() {
        return seq;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getRecordTime() {
        return recordTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getTimePoint() {
        return timePoint;
    }

    public String getBodySignValue() {
        return bodySignValue;
    }

    public String getEncounterIdentifier() {
        return encounterIdentifier;
    }

    public String getBodySignItem() {
        return bodySignItem;
    }

    public String getUnit() {
        return units;
    }

    public void setBodySignItem(String bodySignItem) {
        this.bodySignItem = bodySignItem;
    }

    public void setBodySignValue(String bodySignValue) {
        this.bodySignValue = bodySignValue;
    }

    public void setEncounterIdentifier(String encounterIdentifier) {
        this.encounterIdentifier = encounterIdentifier;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public void setTimePoint(Date timePoint) {
        this.timePoint = timePoint;
    }

    public void setUnit(String unit) {
        this.units = unit;
    }

}
