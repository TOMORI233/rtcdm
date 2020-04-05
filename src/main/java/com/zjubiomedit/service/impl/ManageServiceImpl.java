package com.zjubiomedit.service.impl;

import com.zjubiomedit.dao.Dict.OrgDictRepository;
import com.zjubiomedit.dao.Platform.AlertRecordRepository;
import com.zjubiomedit.dao.Platform.COPDManageDetailRepository;
import com.zjubiomedit.dao.Platform.ManagedPatientIndexRepository;
import com.zjubiomedit.dao.Platform.ManagementApplicationRepository;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserAuthsRepository;
import com.zjubiomedit.dto.DoctorEndDto.DoctorListDto;
import com.zjubiomedit.dto.DoctorEndDto.ManagedPatientCountDto;
import com.zjubiomedit.dto.DoctorEndDto.ReferralApplyDot;
import com.zjubiomedit.dto.DoctorEndDto.RefferalBackDto;
import com.zjubiomedit.dto.PagingDto.AlertPagingDto;
import com.zjubiomedit.dto.PagingDto.ManageIndexPagingDto;
import com.zjubiomedit.dto.PagingDto.RegisterPagingDto;
import com.zjubiomedit.entity.Dict.OrgDict;
import com.zjubiomedit.entity.Platform.AlertRecord;
import com.zjubiomedit.entity.Platform.COPDManageDetail;
import com.zjubiomedit.entity.Platform.ManagedPatientIndex;
import com.zjubiomedit.entity.Platform.ManagementApplicationReview;
import com.zjubiomedit.entity.User.PatientUserAuths;
import com.zjubiomedit.service.ManageService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.ErrorEnum;
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
public class ManageServiceImpl implements ManageService {

    @Autowired
    ManagementApplicationRepository managementApplicationRepository;
    @Autowired
    ManagedPatientIndexRepository managedPatientIndexRepository;
    @Autowired
    PatientUserAuthsRepository patientUserAuthsRepository;
    @Autowired
    DoctorUserAuthsRepository doctorUserAuthsRepository;
    @Autowired
    AlertRecordRepository alertRecordRepository;
    @Autowired
    COPDManageDetailRepository copdManageDetailRepository;
    @Autowired
    OrgDictRepository orgDictRepository;

    @Override
    public Result pagingPatientRegister(Long viewerID, Integer pageIndex, Integer pageOffset) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
        Page<RegisterPagingDto> page;
        page = managementApplicationRepository.findByViewerID(viewerID, pageable);
        return new Result(page);
    }

    @Override
    public Result reviewRegister(Long serialNo, Integer status, Long doctorID, Long reviewerID, String refuseReason) {
        //审核
        Optional<ManagementApplicationReview> registerReview = managementApplicationRepository.findBySerialNo(serialNo);
        if(registerReview.isPresent()){
            ManagementApplicationReview review = registerReview.get();
            if(review.getStatus().equals(Utils.REVIEW_APPROVED) || review.getStatus().equals(Utils.REVIEW_FAILED)){
                return new Result(ErrorEnum.E_30000);
            }
            review.setStatus(status);
            review.setReviewerID(reviewerID);
            Long patientID = review.getPatientID();
            Long hospitalID = review.getHospitalID();
            // doctorID
            Date dt = new Date();
            if(status.equals(Utils.REVIEW_APPROVED)){ //通过
                review.setDoctorID(doctorID);
                // ManagedPatientIndex表添加患者医生关系
                ManagedPatientIndex newIndex = new ManagedPatientIndex();
                newIndex.setPatientID(patientID);
                newIndex.setHospitalID(hospitalID);
                newIndex.setDoctorID(doctorID);
                newIndex.setManageStartDateTime(dt);
                managedPatientIndexRepository.save(newIndex);
                // 修改PatientUserAuths表用户状态
                PatientUserAuths thisPatient = patientUserAuthsRepository.findByUserID(patientID);
                thisPatient.setStatus(Utils.USER_ACTIVE);
                patientUserAuthsRepository.save(thisPatient);
                // COPDManageDetail建立患者管理等级
                COPDManageDetail thisManage = new COPDManageDetail();
                thisManage.setPatientID(patientID);
                copdManageDetailRepository.save(thisManage);
            }
            else if(status == Utils.REVIEW_FAILED){ //不通过
                review.setRefuseReason(refuseReason);
            }
            managementApplicationRepository.save(review);
        }
        else{
            return new Result(ErrorEnum.E_30001);
        }
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
    public Result pagingPatientAlert(Long viewerID, Integer pageIndex, Integer pageOffset) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "alertTime");
        Page<AlertPagingDto> page = alertRecordRepository.findAlertPageByViewerID(viewerID, pageable);
        return new Result(page);
    }

    @Override
    public Result getHospitalList(String orgCode) {
        List<OrgDict> list = orgDictRepository.findByParentOrgCodeAndIsValid(orgCode, Utils.VALID);
        return new Result(list);
    }

    @Override
    public Result getPatientAlertHist(Long patientID, Integer pageIndex, Integer pageOffset) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "alertTime");
        Page<AlertPagingDto> page = alertRecordRepository.findPatientAlertHistByPatientID(patientID, pageable);
        return new Result(page);
    }

    @Override
    public Result getAlertPatientCount(Long viewerID) {
        return new Result(alertRecordRepository.CountPatientByViewerID(viewerID));
    }

    @Override
    public Result ignoreAlert(Long serialNo, String ignoreReason, Long executeDoctorID) {
        AlertRecord thisRecord  = alertRecordRepository.findBySerialNo(serialNo);
        thisRecord.setIgnoreReason(ignoreReason);
        thisRecord.setStatus(Utils.ALERT_IGNORED);
        thisRecord.setExecuteDoctorID(executeDoctorID);
        alertRecordRepository.save(thisRecord);
        return null;
    }

    @Override
    public Result getReferralCount(Long viewerID) {
        return null;
    }

    @Override
    public Result pagingReferralReview(Long viewerID) {
        return null;
    }

    @Override
    public Result applyReferral(ReferralApplyDot referralApplyDot) {
        return null;
    }

    @Override
    public Result backReferral(RefferalBackDto refferalBackDto) {
        return null;
    }

    @Override
    public Result getFollowupCount(Long viewerID) {
        return null;
    }

    @Override
    public Result pagingFollowup(Long viewerID, Date startTime, Date endTime) {
        return null;
    }

    @Override
    public Result ignoreFollowup(Long serialNo) {
        return null;
    }

    @Override
    public Result pagingDoctorManageIndex(Long doctorID, Integer pageIndex, Integer pageOffset, Integer type) {

        Optional<Integer> auth = doctorUserAuthsRepository.findAuthById(doctorID);
        if(auth.isPresent()){
            if(auth.get().equals(Utils.GROUP)){
                return new Result(ErrorEnum.E_401);
            }
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset);
            if(type.equals(Utils.TYPE_ALL)){
                Page<ManageIndexPagingDto> pageAll = managedPatientIndexRepository.findAllManageIndexByDoctorID(doctorID, pageable);
                return new Result(pageAll);
            }
            else if(type.equals(Utils.TYPE_MANAGING)){
                Page<ManageIndexPagingDto> pageManaging = managedPatientIndexRepository.findManagingManageIndexByDoctorID(doctorID, pageable);
                return new Result(pageManaging);
            }
            else if(type.equals(Utils.TYPE_REFERRAL_OUT)){
                Page<ManageIndexPagingDto> pageReferralOut = managedPatientIndexRepository.findReferralOutManageIndexByDoctorID(doctorID, pageable);
                return new Result(pageReferralOut);
            }
            else if(type.equals(Utils.TYPE_REFERRAL_IN)){
                Page<ManageIndexPagingDto> pageReferralIn = managedPatientIndexRepository.findReferralInManageIndexByDoctorID(doctorID, pageable);
                return new Result(pageReferralIn);
            }
            else{
                return new Result(ErrorEnum.E_501);
            }
        }
        else {
            return new Result(ErrorEnum.E_400);
        }
    }

    @Override
    public Result pagingHospitalManageIndex(String orgCode, Integer pageIndex, Integer pageOffset, Integer type) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset);
        if(type.equals(Utils.TYPE_ALL)){
            Page<ManageIndexPagingDto> pageAll = managedPatientIndexRepository.findAllManageIndexByOrgCode(orgCode, pageable);
            return new Result(pageAll);
        }
        else if(type.equals(Utils.TYPE_MANAGING)){
            Page<ManageIndexPagingDto> pageManaging = managedPatientIndexRepository.findManagingManageIndexByOrgCode(orgCode, pageable);
            return new Result(pageManaging);
        }
        else if(type.equals(Utils.TYPE_REFERRAL_OUT)){
            Page<ManageIndexPagingDto> pageReferralOut = managedPatientIndexRepository.findReferralOutManageIndexByOrgCode(orgCode, pageable);
            return new Result(pageReferralOut);
        }
        else if(type.equals(Utils.TYPE_REFERRAL_IN)){
            Page<ManageIndexPagingDto> pageReferralIn = managedPatientIndexRepository.findReferralInManageIndexByOrgCode(orgCode, pageable);
            return new Result(pageReferralIn);
        }
        else{
            return new Result(ErrorEnum.E_501);
        }
    }

    @Override
    public Result getDoctorPatientCount(Long doctorID) {
        Optional<Integer> auth = doctorUserAuthsRepository.findAuthById(doctorID);
        if (auth.isPresent()){
            if(auth.get().equals(Utils.GROUP)){
                return new Result(ErrorEnum.E_401);
            }
            Long managingCount = managedPatientIndexRepository.CountManagingByDoctorID(doctorID);
            Long referralOutCount = managedPatientIndexRepository.CountReferralOutByDoctorID(doctorID);
            Long referralInCount = managedPatientIndexRepository.CountReferralInByDoctorID(doctorID);
            Long totalCount = managingCount + referralOutCount + referralInCount;
            ManagedPatientCountDto patientCountDto = new ManagedPatientCountDto(totalCount, managingCount, referralOutCount, referralInCount);
            return new Result(patientCountDto);
        }
        else {
            return new Result(ErrorEnum.E_400);
        }
    }

    @Override
    public Result getHospitalPatientCount(String orgCode) {
        Long managingCount = managedPatientIndexRepository.CountManagingByOrgCode(orgCode);
        Long referralOutCount = managedPatientIndexRepository.CountReferralOutByOrgCode(orgCode);
        Long referralInCount = managedPatientIndexRepository.CountReferralInByOrgCode(orgCode);
        Long totalCount = managingCount + referralOutCount + referralInCount;
        ManagedPatientCountDto patientCountDto = new ManagedPatientCountDto(totalCount, managingCount, referralOutCount, referralInCount);
        return new Result(patientCountDto);
    }

}
