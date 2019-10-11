package com.zjubiomedit.domain.patient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zjubiomedit.dao.Register.DoctorLoginRepository;
import com.zjubiomedit.dao.patient.BodySignDao;
import com.zjubiomedit.dao.patient.PatientInfoDao;
import com.zjubiomedit.dao.patient.PatientLoginDao;
import com.zjubiomedit.domain.Register.DoctorLogin;
import com.zjubiomedit.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 登录接口返回的病人信息
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
@Component
public class PatientLoginInfo {
    private String patientID;
    private String identityCardNumber;
    private String phoneNumber;
    private String patientName;
    private double newestHeight;
    private double newestWeight;
    private String sexCode;
    private Date birthDate;
    private Date registerDate;
    private String doctorId;
    private String doctorName;

    @Autowired
    transient PatientInfoDao patientInfoDao;
    @Autowired
    transient PatientLoginDao patientLoginDao;
    @Autowired
    transient BodySignDao bodySignDao;
    @Autowired
    transient DoctorLoginRepository doctorLoginRepository;

//    public PatientLoginInfo(String patientID){
//        this.patientID = patientID;
//        if (this.patientInfoDao != null) {
//            System.out.println("inject success");
//        }
//
//    }

    public void getInfo(String patientID) {
        this.patientID = patientID;
        Optional<PatientInfo> optional = patientInfoDao.findById(patientID);
        optional.ifPresent(patientInfo -> {
            this.identityCardNumber = patientInfo.getIdentityCardNumber();
            this.phoneNumber = patientInfo.getPhoneNumber();
            this.patientName = patientInfo.getFullName();
            this.sexCode = patientInfo.getSexCode();
            this.birthDate = patientInfo.getBirthDate();
            this.doctorId = patientInfo.getDoctor();
        });
        Optional<DoctorLogin> docOptional = doctorLoginRepository.findByUserId(this.doctorId);
        docOptional.ifPresent(doctorLogin -> {
            this.doctorName = doctorLogin.getUserName();
        });
        Optional<PatientLogin> infoOptional = patientLoginDao.findByPatientIdentifier(patientID);
        infoOptional.ifPresent(patientLogin -> {
            this.registerDate = patientLogin.getRegisterDate();
        });
        List<BodySign> bodySigns = bodySignDao.findByPatientIdentifier(patientID);
        for (BodySign bodySign: bodySigns) {
            if (bodySign.getBodySignItem().equals(Utils.HEIGHT)){
                this.newestHeight = Double.parseDouble(bodySign.getBodySignValue());
            } else if (bodySign.getBodySignItem().equals(Utils.WEIGHT)){
                this.newestWeight = Double.parseDouble(bodySign.getBodySignValue());
            }
        }
    }

    PatientLoginInfo(){}

    public void setSexCode(String sexCode) {
        this.sexCode = sexCode;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSexCode() {
        return sexCode;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getBirthDate() {
        return birthDate;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setPatientID(String patientId) {
        this.patientID = patientId;
    }

    public String getPatientID() {
        return patientID;
    }

    public double getNewestHeight() {
        return newestHeight;
    }

    public double getNewestWeight() {
        return newestWeight;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setNewestHeight(double newestHeight) {
        this.newestHeight = newestHeight;
    }

    public void setNewestWeight(double newestWeight) {
        this.newestWeight = newestWeight;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

}
