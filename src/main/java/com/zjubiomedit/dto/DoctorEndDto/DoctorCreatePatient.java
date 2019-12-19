package com.zjubiomedit.dto.DoctorEndDto;

import lombok.Data;

import java.util.Date;

@Data
public class DoctorCreatePatient {
    // PatientUserAuths
    private String userName; // 账号名
    private String mobilePhone;
    private String email;
    private String password;
    private Integer status = 10; // 0-正常，1-冻结，10-待审核
    private String weChat;

    // PatientUserBaseInfo
    private Long doctorID; //关联医生ID
    private String name; // 患者姓名
    private Integer sex = 0; // 0-未知, 1-男, 2-女，9-...
    private Date dateOfBirth;
    private String identityCardNumber;
    private String education;
    private String profession;
    private String photo;
    private String phone; //同mobilePhone
    private String address;
    private Integer height; // 单位：cm
    private Float weight; // 单位：kg

    // ManagePatientIndex
    private String patientFeature; //如有多个，之间用逗号分隔，例如：老年人，肥胖，残疾人
    private Integer manageClass; //哪种疾病
    private String orgCode; //管理该患者的机构
    private Long hospitalID; // 关联所属医院集体账号
    private String hospitalName; //关联所属医院名称，即医院集体账号名称
    private String doctorName; //关联所属医生/管理师个人账号名称，即医生/管理师名称
}
