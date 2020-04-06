package com.zjubiomedit.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.zjubiomedit.dao.Platform.ManagedPatientIndexRepository;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserBaseInfoRepository;
import com.zjubiomedit.dto.DoctorEndDto.DoctorCreatePatientDto;
import com.zjubiomedit.dto.DoctorEndDto.DoctorListDto;
import com.zjubiomedit.entity.Platform.ManagedPatientIndex;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.entity.User.PatientUserAuths;
import com.zjubiomedit.entity.User.PatientUserBaseInfo;
import com.zjubiomedit.service.UserService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.ErrorEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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
    @Autowired
    DoctorUserAuthsRepository doctorUserAuthsRepository;

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
    public Result getDoctorList(Long hospitalID){
        Optional<Integer> auth = doctorUserAuthsRepository.findAuthById(hospitalID);
        if(auth.isPresent()){
            if(auth.get().equals(Utils.PERSONAL)){
                return new Result(ErrorEnum.E_401);
            }
            List<DoctorListDto> doctorList = doctorUserAuthsRepository.findByHospitalId(hospitalID);
            return new Result(doctorList);
        }
        else {
            return new Result(ErrorEnum.E_400);
        }
    }

    @Override
    public Result createPatientUser(DoctorCreatePatientDto doctorCreatePatientDto) {
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
        BeanUtils.copyProperties(doctorCreatePatientDto, patientUserAuths);
        BeanUtils.copyProperties(doctorCreatePatientDto, patientUserBaseInfo);
        BeanUtils.copyProperties(doctorCreatePatientDto, managedPatientIndex);
        managedPatientIndex.setPatientID(doctorCreatePatientDto.getUserID());
        patientUserAuthsRepository.save(patientUserAuths);
        PatientUserBaseInfo userBaseInfo = patientUserBaseInfoRepository.save(patientUserBaseInfo);
        managedPatientIndexRepository.save(managedPatientIndex);
        return new Result(userBaseInfo);
    }
}
