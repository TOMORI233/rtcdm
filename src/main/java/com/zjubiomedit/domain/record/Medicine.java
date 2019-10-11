package com.zjubiomedit.domain.record;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/5/16
 */
@Entity(name = "MedicationRecord")
public class Medicine extends GeneralRecord {

    @Column(columnDefinition = "nvarchar")
    private String medicineName;
    @Column(columnDefinition = "nvarchar")
    private String dosage;
    //    private Date measureTime;
    @Column(columnDefinition = "nvarchar")
    private String memo;
    private Integer executeFlag;

    public Medicine() {
    }

    public Medicine(String patientIdentifier, String medicineName, String dosage, Date measureTime, String memo) {
        super(patientIdentifier, measureTime);
        this.medicineName = medicineName;
        this.dosage = dosage;
        this.memo = memo;
        this.executeFlag = 0;
    }


    public void setPatientIdentifier(String patientIdentifier) {
        patientIdentifier = patientIdentifier;
    }

    public int getExecuteFlag() {
        return executeFlag;
    }

    public void setExecuteFlag(int executeFlag) {
        executeFlag = executeFlag;
    }

    public String getDosage() {
        return dosage;
    }

    public String getMemo() {
        return memo;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }

    public void setMedicineName(String medicineName) {
        medicineName = medicineName;
    }


}
