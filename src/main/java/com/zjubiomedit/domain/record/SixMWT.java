package com.zjubiomedit.domain.record;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
@Entity(name = "SixMWTRecord")
public class SixMWT extends GeneralRecord {
    private Integer value;
    @Column(columnDefinition = "nvarchar")
    private String answers;
    private Integer flag = null;

    public SixMWT(String patientId, Date measureTime, int value, String answers) {
        super(patientId, measureTime);
        this.value = value;
        this.answers = answers;
    }

    public SixMWT(){}

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public String getAnswers() {
        return answers;
    }
}
