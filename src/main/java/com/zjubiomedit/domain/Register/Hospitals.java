package com.zjubiomedit.domain.Register;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Hospitals {
    private int seq;
    private String hospitalCode;
    private String hospitalName;
    private String provinceCode;

    @Id
    @Column(name = "Seq", nullable = false)
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "HospitalCode", nullable = false, length = 30)
    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    @Basic
    @Column(name = "HospitalName", nullable = false, length = 30)
    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    @Basic
    @Column(name = "provinceCode", nullable = true, length = 30)
    public String getprovinceCode() {
        return provinceCode;
    }

    public void setprovinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hospitals hospitals = (Hospitals) o;
        return seq == hospitals.seq &&
                Objects.equals(hospitalCode, hospitals.hospitalCode) &&
                Objects.equals(hospitalName, hospitals.hospitalName) &&
                Objects.equals(provinceCode, hospitals.provinceCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, hospitalCode, hospitalName, provinceCode);
    }
}
