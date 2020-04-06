package com.zjubiomedit.service;

import com.zjubiomedit.dto.DoctorEndDto.FollowupPlanCreateDto;
import com.zjubiomedit.dto.DoctorEndDto.FollowupRecordDto;
import com.zjubiomedit.dto.DoctorEndDto.ReferralApplyDto;
import com.zjubiomedit.util.Result;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface ManageService {
    /**
     *  审核患者分页
     * @return
     */
    Result pagingPatientRegister(Long viewerID, Integer pageIndex, Integer pageOffset);

    /**
     *  审核患者
     */
    Result reviewRegister(Long serialNo, Integer status, Long doctorID, Long reviewerID, String refuseReason);

    Result pagingPatientAlert(Long viewerID, Integer pageIndex, Integer pageOffset);

    Result getPatientAlertHist(Long patientID, Integer pageIndex, Integer pageOffset);

    Result getAlertPatientCount(Long viewerID);

    Result ignoreAlert(Long serialNo, String ignoreReason, Long executeDoctorID);

    Result pagingReferralReview(Long viewerID, Integer pageIndex, Integer pageOffset);

    Result applyReferral(ReferralApplyDto referralApplyDto);

    Result getFollowupCount(Long viewerID, Date startDate, Date endDate);

    Result pagingFollowup(Long viewerID, Integer status, Date startDate, Date endDate, Integer pageIndex, Integer pageOffset);

    Result ignoreFollowup(Long serialNo);

    Result pagingDoctorManageIndex(Long doctorID, Integer pageIndex, Integer pageOffset, Integer type);

    Result pagingHospitalManageIndex(String orgCode, Integer pageIndex, Integer pageOffset, Integer type);

    Result getDoctorPatientCount(Long doctorID);

    Result getHospitalPatientCount(String orgCode);

    Result getPatientList(Long viewerID);

    Result createFollowupPlan(FollowupPlanCreateDto followupPlanCreateDto);

    Result refuseReferral(Long serialNo, Long reviewerID, String refuseReason);

    Result approveReferral(Long serialNo, Long reviewerID, Long doctorID);

    Result backReferral(Long serialNo, String receipt);

    Result recordPatientFollowup(FollowupRecordDto followupRecordDto);
}
