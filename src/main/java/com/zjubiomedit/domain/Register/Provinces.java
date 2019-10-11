package com.zjubiomedit.domain.Register;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Provinces {
    private int seq;
    private String provinceCode;
    private String provinceName;

    @Id
    @Column(name = "Seq", nullable = false)
    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    @Basic
    @Column(name = "ProvinceCode", nullable = false, length = 8)
    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    @Basic
    @Column(name = "ProvinceName", nullable = false, length = 20)
    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Provinces provinces = (Provinces) o;
        return seq == provinces.seq &&
                Objects.equals(provinceCode, provinces.provinceCode) &&
                Objects.equals(provinceName, provinces.provinceName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(seq, provinceCode, provinceName);
    }
}
