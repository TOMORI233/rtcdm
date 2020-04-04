package com.zjubiomedit.service.impl;

import com.zjubiomedit.dao.Platform.AlertRecordRepository;
import com.zjubiomedit.dao.Platform.COPDManageDetailRepository;
import com.zjubiomedit.dao.Platform.ManagedPatientIndexRepository;
import com.zjubiomedit.dao.Platform.ManagementApplicationRepository;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserAuthsRepository;
import com.zjubiomedit.dto.DoctorEndDto.DoctorListDto;
import com.zjubiomedit.dto.DoctorEndDto.ReferralApplyDot;
import com.zjubiomedit.dto.DoctorEndDto.RefferalBackDto;
import com.zjubiomedit.dto.PagingDto.AlertPagingDto;
import com.zjubiomedit.dto.PagingDto.RegisterPagingDto;
import com.zjubiomedit.entity.Platform.AlertRecord;
import com.zjubiomedit.entity.Platform.COPDManageDetail;
import com.zjubiomedit.entity.Platform.ManagedPatientIndex;
import com.zjubiomedit.entity.Platform.ManagementApplicationReview;
import com.zjubiomedit.entity.User.PatientUserAuths;
import com.zjubiomedit.service.ManageService;
import com.zjubiomedit.util.Result;
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

    @Override
    public Result pagingPatientRegister(Long hospitalID, Long doctorID, Integer pageIndex, Integer pageOffset) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
        Page<RegisterPagingDto> page;
        if(doctorID == null){
            page = managementApplicationRepository.findByHospitalID(hospitalID, pageable);
        }
        else{
            page = managementApplicationRepository.findByDoctorID(doctorID, pageable);
        }
        return new Result(page);
    }

    @Override
    public Result reviewRegister(Long serialNo, Integer status, Long doctorID, Long reviewerID, String refuseReason) {
//        //审核
        Optional<ManagementApplicationReview> registerReview = managementApplicationRepository.findBySerialNo(serialNo);
        if(registerReview.isPresent()){
            ManagementApplicationReview review = registerReview.get();
            if(review.getStatus() == 1 || review.getStatus() == 2){
                return new Result("该患者已审核");
            }
            review.setStatus(status);
            review.setReviewerID(reviewerID);
            Long patientID = review.getPatientID();
            Long hospitalID = review.getHospitalID();
            // doctorID
            Date dt = new Date();
            if(status == 1){ //通过
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
                thisPatient.setStatus(0);
                patientUserAuthsRepository.save(thisPatient);
                // COPDManageDetail建立患者管理等级
                COPDManageDetail thisManage = new COPDManageDetail();
                thisManage.setPatientID(patientID);
                copdManageDetailRepository.save(thisManage);
            }
            else if(status == 2){ //不通过
                review.setRefuseReason(refuseReason);
            }
            managementApplicationRepository.save(review);
        }
        else{
            return new Result("记录不存在"); //枚举类待存
        }
        return new Result("审核完成");
    }

    @Override
    public Result getDoctorList(Long hospitalID){
        List<DoctorListDto> doctorList = doctorUserAuthsRepository.findByHospitalId(hospitalID);
        return new Result(doctorList);
    }

    @Override
    public Result pagingPatientAlert(Long viewerID, Integer pageIndex, Integer pageOffset) {
        Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "alertTime");
        Page<AlertPagingDto> page = alertRecordRepository.findAlertPageByViewerID(viewerID, pageable);
        return new Result(page);
    }

    @Override
    public Result pagingPatientManageIndex(Long viewerID) {
        return null;
    }

    @Override
    public Result getHospitalList(Long viewerID) {
        return null;
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
        thisRecord.setStatus(2);
        thisRecord.setExecuteDoctorID(executeDoctorID);
        alertRecordRepository.save(thisRecord);
        return new Result("已忽略该条预警");
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

}
