package com.zjubiomedit.service;

import com.google.gson.JsonObject;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.util.Result;

public interface UserService {
    /**
     * 新建医生账号
     */
     Result createDoctorUser(DoctorUserAuths jsonObject);

    /**
     * 医生平台新建患者账号，不需要审核，创建默认管理计划
     */
    Result createPatientUser(JsonObject jsonObject);

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

}
