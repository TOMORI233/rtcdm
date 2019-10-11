package com.zjubiomedit.domain.patient;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
@Entity(name = "PatientBasics")
public class PatientInfo {
    private final static int MANAGE_STATE = 10; // 待审核

    @Id
    @Column(columnDefinition = "nvarchar")
    private String patientIdentifier;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String fullName;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String profession;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String sexCode;
    @Column(nullable = false)
    private Date birthDate;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String education;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String identityCardNumber;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String detailedAddress;
    @Column(columnDefinition = "nvarchar", nullable = false)
    private String phoneNumber;
    @Column(columnDefinition = "nvarchar")
    private String doctor;
    @Column(columnDefinition = "nvarchar")
    private String manageDoctor;
    @Column(columnDefinition = "nvarchar")
    private Integer manageMark;
    @Column(columnDefinition = "nvarchar")
    private String nationalityCode;
    @Column(columnDefinition = "nvarchar")
    private String ethnicCode;
    @Column(columnDefinition = "nvarchar")
    private String maritalStatus;
    @Column(columnDefinition = "nvarchar")
    private String bloodType;
    @Column(columnDefinition = "nvarchar")
    private String healthCardNumber;
    @Column
    private Boolean smoke;

    public PatientInfo(String patientIdentifier, String fullName, String sexCode, Date birthDate, String profession, String education, String identityCardNumber, String detailedAddress, String phoneNumber, String doctor, String manageDoctor, boolean smoke) {
        this.patientIdentifier = patientIdentifier;
        this.fullName = fullName;
        this.sexCode = sexCode;
        this.birthDate = birthDate;
        this.profession = profession;
        this.education = education;
        this.identityCardNumber = identityCardNumber;
        this.detailedAddress = detailedAddress;
        this.phoneNumber = phoneNumber;
        this.doctor = doctor;
        this.manageDoctor = manageDoctor;
        this.manageMark = MANAGE_STATE;
        this.smoke = smoke;
    }

    PatientInfo(){}

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public String getPatientIdentifier() {
        return patientIdentifier;
    }

    public void setPatientIdentifier(String patientIdentifier) {
        this.patientIdentifier = patientIdentifier;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getBirthDate() {
        return birthDate;
    }

    public Integer getManageMark() {
        return manageMark;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getEducation() {
        return education;
    }

    public String getEthnicCode() {
        return ethnicCode;
    }

    public String getFullName() {
        return fullName;
    }

    public String getHealthCardNumber() {
        return healthCardNumber;
    }

    public String getManageDoctor() {
        return manageDoctor;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public String getProfession() {
        return profession;
    }

    public String getSexCode() {
        return sexCode;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setDetailedAdress(String detailedAdress) {
        this.detailedAddress = detailedAddress;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public void setEthnicCode(String ethnicCode) {
        this.ethnicCode = ethnicCode;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setHealthCardNumber(String healthCardNumber) {
        this.healthCardNumber = healthCardNumber;
    }

    public void setManageDoctor(String manageDoctor) {
        this.manageDoctor = manageDoctor;
    }

    public void setManageMark(Integer manageMark) {
        this.manageMark = manageMark;
    }

    public void setMaritalStatus(String maritialStatus) {
        this.maritalStatus = maritialStatus;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    public Boolean getSmoke() {
        return smoke;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public void setSmoke(Boolean smoke) {
        this.smoke = smoke;
    }
}

