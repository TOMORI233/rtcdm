package com.zjubiomedit.service.impl;

import com.zjubiomedit.config.exception.CommonJsonException;
import com.zjubiomedit.dao.Platform.*;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserBaseInfoRepository;
import com.zjubiomedit.dto.DoctorEndDto.DoctorCreatePatientDto;
import com.zjubiomedit.dto.DoctorEndDto.DoctorListDto;
import com.zjubiomedit.entity.Platform.*;
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

import java.util.Date;
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
        try {
            Optional<DoctorUserAuths> userAuthsOptional = doctorUserAuthsRepository.findByUserName(doctorUserAuths.getUserName());
            if (userAuthsOptional.isPresent()) {
                return new Result(ErrorEnum.E_10003);
            }
            DoctorUserAuths newDoctor = doctorUserRepository.save(doctorUserAuths);
            return new Result(newDoctor);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result getPatientBaseInfo(Long patientID) {
        try {
            return new Result(patientUserBaseInfoRepository.findByUserID(patientID));
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getDoctorList(Long hospitalID) {
        try {
            Optional<Integer> auth = doctorUserAuthsRepository.findAuthById(hospitalID);
            if (auth.isPresent()) {
                if (auth.get().equals(Utils.PERSONAL)) {
                    return new Result(ErrorEnum.E_401);
                }
                List<DoctorListDto> doctorList = doctorUserAuthsRepository.findByHospitalId(hospitalID);
                return new Result(doctorList);
            } else {
                return new Result(ErrorEnum.E_400);
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getPatientManageDetail(Long patientID) {
        try {
            return new Result(managedPatientIndexRepository.findByPatientID(patientID));
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getPatientReferralDetail(Long patientID) {
        try {
            return new Result(referralRecordRepository.findFirstByPatientIDOrderByStartDateTimeDesc(patientID));
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getDoctorNameByDoctorID(Long doctorID) {
        try {
            return new Result(doctorUserAuthsRepository.findDoctorNameByDoctorID(doctorID));
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getPatientManagePlanHistory(Long patientID) {
        try {
            return new Result(managementPlanRepository.findByPatientID(patientID));
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getPatientAlertHistory(Long patientID, Integer pageIndex, Integer pageOffset) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
            Page<AlertRecord> page = alertRecordRepository.findByPatientID(patientID, pageable);
            return new Result(page);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getPatientFollowupHistory(Long patientID, Integer pageIndex, Integer pageOffset) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
            Page<FollowupRecord> page = followupRecordRepository.findByPatientID(patientID, pageable);
            return new Result(page);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getPatientReferralHistory(Long patientID, Integer pageIndex, Integer pageOffset) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
            Page<ReferralRecord> page = referralRecordRepository.findByPatientID(patientID, pageable);
            return new Result(page);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result createPatientUser(DoctorCreatePatientDto doctorCreatePatientDto) {
        try {
            // 存入PatientUserAuths表并生成userID
            PatientUserAuths patientUserAuths = new PatientUserAuths();
            BeanUtils.copyProperties(doctorCreatePatientDto, patientUserAuths);
            patientUserAuths.setStatus(Utils.USER_ACTIVE);
            PatientUserAuths savePatientUser = patientUserAuthsRepository.save(patientUserAuths);
            Long patientID = savePatientUser.getUserID();
            // 存入PatientUserBaseInfo表
            PatientUserBaseInfo patientUserBaseInfo = new PatientUserBaseInfo();
            BeanUtils.copyProperties(doctorCreatePatientDto, patientUserBaseInfo);
            patientUserBaseInfo.setPhone(savePatientUser.getMobilePhone());
            patientUserBaseInfo.setUserID(patientID);
            patientUserBaseInfoRepository.save(patientUserBaseInfo);
            // 存入ManagePatientIndex表
            ManagedPatientIndex managedPatientIndex = new ManagedPatientIndex();
            BeanUtils.copyProperties(doctorCreatePatientDto, managedPatientIndex);
            managedPatientIndex.setPatientID(patientID);
            managedPatientIndex.setManageStartDateTime(new Date());
            String orgCode = doctorCreatePatientDto.getOrgCode();
            Optional<DoctorUserAuths> optional = doctorUserAuthsRepository.findFirstByOrgCodeAndAuthAndStatus(orgCode, Utils.GROUP, Utils.USER_ACTIVE);
            optional.ifPresent(doctorUserAuths -> {
                managedPatientIndex.setHospitalID(doctorUserAuths.getUserID());
            });
            managedPatientIndexRepository.save(managedPatientIndex);
            // COPDManageDetail建立患者管理等级
            COPDManageDetail copdManageDetail = new COPDManageDetail();
            copdManageDetail.setPatientID(patientID);
            copdManageDetailRepository.save(copdManageDetail);
            return new Result();
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }
}
