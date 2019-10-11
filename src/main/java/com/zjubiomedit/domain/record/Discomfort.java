package com.zjubiomedit.domain.record;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/5/16
 */
@Entity(name = "DiscomfortRecord")
public class Discomfort extends GeneralRecord{

    private int inflammation;
    private int lung;
    private int heart;
    private int breath;
    @Column(columnDefinition = "nvarchar")
    private String memo;
    Integer ExecuteMark = null;

    public Discomfort(){}

    public Discomfort(String patientIdentifier, int inflammation, int lung, int heart, int breath, Date measureTime, String memo){
        super(patientIdentifier, measureTime);
        this.inflammation = inflammation;
        this.lung = lung;
        this.heart = heart;
        this.breath = breath;
        this.memo = memo;
    }


    public void setMemo(String memo) {
        this.memo = memo;
    }


    public String getMemo() {
        return memo;
    }

    public int getBreath() {
        return breath;
    }

    public int getHeart() {
        return heart;
    }

    public int getInflammation() {
        return inflammation;
    }

    public int getLung() {
        return lung;
    }

    public void setBreath(int breath) {
        this.breath = breath;
    }

    public void setHeart(int heart) {
        this.heart = heart;
    }

    public void setInflammation(int inflammation) {
        this.inflammation = inflammation;
    }

    public void setLung(int lung) {
        this.lung = lung;
    }
}
