package com.zjubiomedit.service.impl;

import com.zjubiomedit.dao.Platform.*;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserBaseInfoRepository;
import com.zjubiomedit.dto.DoctorEndDto.DoctorCreatePatientDto;
import com.zjubiomedit.dto.DoctorEndDto.DoctorListDto;
import com.zjubiomedit.entity.Platform.AlertRecord;
import com.zjubiomedit.entity.Platform.FollowupRecord;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    COPDManageDetailRepository copdManageDetailRepository;
    @Autowired
    DoctorUserAuthsRepository doctorUserAuthsRepository;
    @Autowired
    ReferralRecordRepository referralRecordRepository;
    @Autowired
    ManagementPlanRepository managementPlanRepository;
    @Autowired
    AlertRecordRepository alertRecordRepository;
    @Autowired
    FollowupRecordRepository followupRecordRepository;

    @Override
    public Result createDoctorUser(DoctorUserAuths doctorUserAuths) {
        DoctorUserAuths save = doctorUserRepository.save(doctorUserAuths);
        return new Result(save);
    }

    @Override
    public Result getPatientBaseInfo(Long patientID) {
        return new Result(patientUserBaseInfoRepository.findByUserID(patientID));
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
    public Result getPatientManageDetail(Long patientID) {
        return new Result(managedPatientIndexRepository.findByPatientID(patientID));
    }

    @Override
    public Result getPatientReferralDetail(Long patientID) {
        return new Result(referralRecordRepository.findByPatientID(patientID));
    }

    @Override
    public Result getDoctorNameByDoctorID(Long doctorID) {
        return new Result(doctorUserAuthsRepository.findDoctorNameByDoctorID(doctorID));
    }

    @Override
    public Result getPatientManagePlanDetail(Long patientID) {
        return new Result(managementPlanRepository.findByPatientID(patientID));
    }

    @Override
    public Result getPatientAlertDetail(Long patientID, Integer pageIndex, Integer pageOffset) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
        Page<AlertRecord> page = alertRecordRepository.findByPatientID(patientID, pageable);
        return new Result(page);
    }

    @Override
    public Result getPatientFollowupDetail(Long patientID, Integer pageIndex, Integer pageOffset) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
        Page<FollowupRecord> page = followupRecordRepository.findByPatientID(patientID, pageable);
        return new Result(page);
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
