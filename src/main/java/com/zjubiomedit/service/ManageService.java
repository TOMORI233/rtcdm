package com.zjubiomedit.service;

import com.zjubiomedit.dto.DoctorEndDto.ReferralApplyDot;
import com.zjubiomedit.dto.DoctorEndDto.RefferalBackDto;
import com.zjubiomedit.util.Result;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface ManageService {
    /**
     *  审核患者分页
     * @return
     */
    Result pagingPatientRegister(Long hospitalID, Long doctorID, Integer pageIndex, Integer pageOffset);

    /**
     *  审核患者
     */
    Result reviewRegister(Long serialNo, Integer status, Long doctorID, Long reviewerID, String refuseReason);

    Result getDoctorList(Long hospitalID);

    Result pagingPatientAlert(Long viewerID, Integer pageIndex, Integer pageOffset);

    Result pagingPatientManageIndex(Long viewerID);

    Result getHospitalList(Long viewerID);

    Result getPatientAlertHist(Long patientID, Integer pageIndex, Integer pageOffset);

    Result getAlertPatientCount(Long viewerID);

    Result ignoreAlert(Long serialNo, String ignoreReason, Long executeDoctorID);

    Result getReferralCount(Long viewerID);

    Result pagingReferralReview(Long viewerID);

    Result applyReferral(ReferralApplyDot referralApplyDot);

    Result backReferral(RefferalBackDto refferalBackDto);

    Result getFollowupCount(Long viewerID);

    Result pagingFollowup(Long viewerID, Date startTime, Date endTime);

    Result ignoreFollowup(Long serialNo);
}
