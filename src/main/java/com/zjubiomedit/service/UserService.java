package com.zjubiomedit.service;

import com.zjubiomedit.dto.DoctorEndDto.DoctorCreatePatientDto;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.util.Result;

public interface UserService {
    /**
     * 新建医生账号
     */
    Result createDoctorUser(DoctorUserAuths doctorUserAuths);

    /**
     * 医生平台新建患者账号，不需要审核，创建默认管理计划
     */
    Result createPatientUser(DoctorCreatePatientDto doctorCreatePatientDto);

    /**
     * 修改医生账号信息
     */
//    Result updateDoctorUser(JsonObject jsonObject);

    /**
     * 修改患者账号信息
     */
//    Result updatePatientUser(JsonObject jsonObject);
    
    Result getPatientBaseInfo(Long patientID);

    Result getDoctorList(Long hospitalID);

    Result getPatientManageDetail(Long patientID);

    Result getPatientReferralDetail(Long patientID);

    Result getDoctorNameByDoctorID(Long doctorID);

    Result getPatientManagePlanHistory(Long patientID);

    Result getPatientAlertHistory(Long patientID, Integer pageIndex, Integer pageOffset);

    Result getPatientFollowupHistory(Long patientID, Integer pageIndex, Integer pageOffset);

    Result getPatientReferralHistory(Long patientID, Integer pageIndex, Integer pageOffset);

    Result getOrgNameByDoctorID(Long doctorID);

    Result getDoctorListByOrgCode(String orgCode);
}
