package com.zjubiomedit.dto.DoctorEndDto;

import lombok.Data;

import java.util.Date;

@Data
public class DoctorCreatePatientDto {
    // PatientUserAuths
    private String userName; // 账号名
    private String mobilePhone;
    private String email;
    private String password;
    private String weChat;

    // PatientUserBaseInfo
    private String name; // 患者姓名
    private Integer sex; // 0-未知, 1-男, 2-女，9-...
    private Date dateOfBirth;
    private String identityCardNumber;
    private String education;
    private String profession;
    private String address;
    private Integer height; // 单位：cm
    private Float weight; // 单位：kg

    // ManagePatientIndex
    private Long doctorID; // 关联医生ID
    private String patientFeature; //如有多个，之间用逗号分隔，例如：老年人，肥胖，残疾人

    // 用于获取hospitalID
    private String orgCode; //管理该患者的机构代码
}
