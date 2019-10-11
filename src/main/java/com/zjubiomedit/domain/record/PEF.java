package com.zjubiomedit.domain.record;

import javax.persistence.Entity;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/8
 */
@Entity(name = "PEFRecord")
public class PEF extends GeneralRecord {
    private Integer value;
    private Integer flag = null;

    public PEF(String patientId, Date measureTime, int value) {
        super(patientId, measureTime);
        this.value = value;
    }

    public PEF(){}

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
