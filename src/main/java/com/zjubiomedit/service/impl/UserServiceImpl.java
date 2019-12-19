package com.zjubiomedit.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zjubiomedit.dao.Platform.ManagedPatientIndexRepository;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserBaseInfoRepository;
import com.zjubiomedit.dto.DoctorEndDto.DoctorCreatePatient;
import com.zjubiomedit.entity.Platform.ManagedPatientIndex;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.entity.User.PatientUserAuths;
import com.zjubiomedit.entity.User.PatientUserBaseInfo;
import com.zjubiomedit.service.UserService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.Utils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    DoctorUserAuthsRepository doctorUserRepository;
    @Autowired
    PatientUserAuthsRepository patientUserAuthsRepository;
    @Autowired
    PatientUserBaseInfoRepository patientUserBaseInfoRepository;
    @Autowired
    ManagedPatientIndexRepository managedPatientIndexRepository;

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
    public Result getPatientBaseInfo(JsonObject jsonObject) {

        return null;
    }

    @Override
    public Result createPatientUser(DoctorCreatePatient doctorCreatePatient) {
//        Gson gson = new Gson();
//        PatientUserAuths patientUserAuths = gson.fromJson(jsonObject, PatientUserAuths.class);
//        PatientUserBaseInfo patientUserBaseInfo = gson.fromJson(jsonObject, PatientUserBaseInfo.class);
//        PatientUserAuths userAuths = patientUserAuthsRepository.save(patientUserAuths);
//        patientUserBaseInfo.setUserID(userAuths.getUserID());
//        PatientUserBaseInfo userBaseInfo = patientUserBaseInfoRepository.save(patientUserBaseInfo);
//        return new Result(userBaseInfo);

        PatientUserAuths patientUserAuths = new PatientUserAuths();
        PatientUserBaseInfo patientUserBaseInfo = new PatientUserBaseInfo();
        ManagedPatientIndex managedPatientIndex = new ManagedPatientIndex();
        BeanUtils.copyProperties(doctorCreatePatient, patientUserAuths);
        BeanUtils.copyProperties(doctorCreatePatient, patientUserBaseInfo);
        BeanUtils.copyProperties(doctorCreatePatient, managedPatientIndex);
        patientUserAuthsRepository.save(patientUserAuths);
        PatientUserBaseInfo userBaseInfo = patientUserBaseInfoRepository.save(patientUserBaseInfo);
        managedPatientIndexRepository.save(managedPatientIndex);
        return new Result(userBaseInfo);
    }
}
