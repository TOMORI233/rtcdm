package com.zjubiomedit.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserBaseInfoRepository;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.entity.User.PatientUserAuths;
import com.zjubiomedit.entity.User.PatientUserBaseInfo;
import com.zjubiomedit.entity.User.QDoctorUserAuths;
import com.zjubiomedit.service.UserService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    DoctorUserAuthsRepository doctorUserRepository;
    @Autowired
    PatientUserAuthsRepository patientUserAuthsRepository;
    @Autowired
    PatientUserBaseInfoRepository patientUserBaseInfoRepository;

    @Override
    public Result createDoctorUser(DoctorUserAuths doctorUserAuths) {
        DoctorUserAuths save = doctorUserRepository.save(doctorUserAuths);
        return new Result(save);
    }

    @Override
    public Result getDoctorUserByOrgCode(JsonObject jsonObject) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String orgCode = jsonObject.get("orgCode").getAsString();
        List<DoctorUserAuths> list = doctorUserRepository.findByOrgCodeAndStatus(orgCode, Utils.USER_ACTIVE);
        return new Result(gson.toJson(list));
    }

    @Override
    public Result createPatientUser(JsonObject jsonObject) {
        Gson gson = new Gson();
        PatientUserAuths patientUserAuths = gson.fromJson(jsonObject, PatientUserAuths.class);
        PatientUserBaseInfo patientUserBaseInfo = gson.fromJson(jsonObject, PatientUserBaseInfo.class);
        PatientUserAuths userAuths = patientUserAuthsRepository.save(patientUserAuths);
        patientUserBaseInfo.setUserID(userAuths.getUserID());
        PatientUserBaseInfo userBaseInfo = patientUserBaseInfoRepository.save(patientUserBaseInfo);
        return new Result(userBaseInfo);
    }
}