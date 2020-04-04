package com.zjubiomedit.service;

import com.google.gson.JsonObject;
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

    /**
     * 根据医疗机构代码OrgCode获取医生集体and个人账号
     */
    Result getDoctorUserByOrgCode(JsonObject jsonObject);

    /**
     *  根据医生id获取其管理的患者的基本信息
     */
    Result getPatientBaseInfo(JsonObject jsonObject);
}
