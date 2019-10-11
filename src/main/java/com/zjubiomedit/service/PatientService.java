package com.zjubiomedit.service;

import org.springframework.stereotype.Service;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
@Service
public interface PatientService {
    /**
     * 患者登录
     * @param patientId 病人号
     * @param password 密码
     * @return
     */
    String doLogin(String patientId, String password);

    /**
     * 注册验证，验证病人号的合法性
     * @param patientId 病人号
     * @param name 姓名
     * @return
     */
    String validRegister(String patientId, String name);

    /**
     * 患者注册
     * @param patientId 病人号
     * @param info 注册信息 json string
     * @return
     */
    String doRegister(String patientId, String info);

    // 注册相关
    Object getAllProvince();

    Object getAllHospital(String province);

    Object getAllDoctor(String hospital);

}
