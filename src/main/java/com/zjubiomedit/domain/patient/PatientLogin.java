package com.zjubiomedit.domain.patient;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
@Entity
public class PatientLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String patientIdentifier;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String name;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String password;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String identityCardNumber;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String nickName;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private Date registDate;
    private Integer isDisabled;
    @Column(columnDefinition = "varbinary")
    private Byte[] userPhoto;

    public PatientLogin(String patientIdentifier, String name, String password, String identityCardNumber, String nickName, String phoneNumber, Date registerDate) {
        this.patientIdentifier = patientIdentifier;
        this.name = name;
        this.password = password;
        this.identityCardNumber = identityCardNumber;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.registDate = registerDate;
        this.isDisabled = 0;
    }

    PatientLogin(){}

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getRegisterDate() {
        return registDate;
    }

    public Integer getIsDisabled() {
        return isDisabled;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public String getNickName() {
        return nickName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Byte[] getUserPhoto() {
        return userPhoto;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public void setIsDisabled(Integer isDisabled) {
        this.isDisabled = isDisabled;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRegisterDate(Date registerDate) {
        this.registDate = registerDate;
    }

    public void setUserPhoto(Byte[] userPhoto) {
        this.userPhoto = userPhoto;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
