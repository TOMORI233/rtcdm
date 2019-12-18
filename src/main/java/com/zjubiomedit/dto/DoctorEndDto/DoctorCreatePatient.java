package com.zjubiomedit.dto.DoctorEndDto;

import com.zjubiomedit.entity.User.PatientUserAuths;
import lombok.Data;

import java.util.Date;

@Data
public class DoctorCreatePatient extends PatientUserAuths {
    // PatientUserBaseInfo
    private Long serialNo;
    private Long doctorID;
    private String name;
    private Integer sex = 0; // 0-未知, 1-男, 2-女，9-...
    private Date dateOfBirth;
    private String identityCardNumber;
    private String education;
    private String profession;
    private String photo;
    private String phone;
    private String address;
    private Integer height; // 单位：cm
    private Float weight; // 单位：kg
}
