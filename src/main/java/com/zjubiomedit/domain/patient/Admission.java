package com.zjubiomedit.domain.patient;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/12
 */
@Entity
public class Admission {
    @Id
    @Column(columnDefinition = "nvarchar")
    private String patientIdentifier;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String encounterIdentifier;
    @Column(columnDefinition = "nvarchar")
    private String patientFrom;
    @Column(nullable = false)
    private Date admitDateTime;

    Admission(){}

    public Admission(String patientIdentifier, Date admitDateTime){
        this.patientIdentifier = patientIdentifier;
        this.admitDateTime = admitDateTime;
        this.encounterIdentifier = "1";
    }

    public void setEncounterIdentifier(String encounterIdentifier) {
        this.encounterIdentifier = encounterIdentifier;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getAdmitDateTime() {
        return admitDateTime;
    }

    public String getEncounterIdentifier() {
        return encounterIdentifier;
    }

    public String getPatientFrom() {
        return patientFrom;
    }

    public void setAdmitDateTime(Date admitDateTime) {
        this.admitDateTime = admitDateTime;
    }

    public void setPatientFrom(String patientFrom) {
        this.patientFrom = patientFrom;
    }
}
