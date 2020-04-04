package com.zjubiomedit.controller;

import com.zjubiomedit.dto.DoctorEndDto.ReferralApplyDot;
import com.zjubiomedit.dto.DoctorEndDto.RefferalBackDto;
import com.zjubiomedit.service.impl.ManageServiceImpl;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
@Api(tags = "【医生】患者管理")
@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private ManageServiceImpl manageService;

    /**
     * 审核
     */
    @ApiOperation(value = "【医生】获取待审核患者信息（分页）", response = Result.class)
    @GetMapping(value = "/register/page")
    public Result patientRegisterList(@RequestParam(value = "doctorID", required = false) Long doctorID, //权限设计待更改
                                      @RequestParam(value = "hospitalID") Long hospitalID,
                                      @RequestParam(value = "pageIndex") Integer pageIndex,
                                      @RequestParam(value = "pageOffset") Integer pageOffset){
        return manageService.pagingPatientRegister(hospitalID, doctorID, pageIndex, pageOffset);
    }

    @ApiOperation(value = "【医生】审核（通过/拒绝）患者", response = Result.class)
    @PostMapping(value = "/register/audit")
    public Result patientRegisterAudit(@RequestParam(value = "serialNo") Long serialNo,
                                       @RequestParam(value = "status") Integer status,
                                       @RequestParam(value = "doctorID") Long doctorID,
                                       @RequestParam(value = "reviewerID") Long reviewerID,
                                       @RequestParam(value = "refuseReason", required = false) String refuseReason){
        return manageService.reviewRegister(serialNo, status, doctorID, reviewerID, refuseReason);
    }

    @ApiOperation(value = "【医院】获取本院医生列表", response = Result.class)
    @GetMapping(value = "/register/doctor")
    public Result doctorSelectList(@RequestParam(value = "hospitalID") Long hospitalID){
        return manageService.getDoctorList(hospitalID);
    }

    /**
     *  管理
     */
    @ApiOperation(value = "【医生】获取患者管理索引信息（分页）", response = Result.class)
    @GetMapping(value = "/index/page")
    public Result patientManageIndexPage(@RequestParam(value = "viewerID") Long viewerID){
        return manageService.pagingPatientManageIndex(viewerID);
    }

    @ApiOperation(value = "【医生】获取下属医院列表", response = Result.class)
    @GetMapping(value = "/index/hospital")
    public Result hospitalSelectList(@RequestParam(value = "viewerID") Long viewerID){
        return manageService.getHospitalList(viewerID);
    }

//    @ApiOperation(value = "【医生】获取下属医院列表", response = Result.class)
//    @GetMapping(value = "/index/hospital")
//    public Result hospitalSelectList(@RequestParam(value = "viewerID") Long viewerID){
//        return manageService.getHospitalList(viewerID);
//    }

    /**
     * 随访
     */
    @ApiOperation(value = "【医生】获取随访总人数/待随访人数/已随访人数", response = Result.class)
    @GetMapping(value = "/followup/count")
    public Result patientfollowupCount(@RequestParam(value = "viewerID") Long viewerID){
        return manageService.getFollowupCount(viewerID);
        //返回dto:totalcount,tofollowup,alreadyfollowup
    }

    @ApiOperation(value = "【医生】获取随访列表（分页）", response = Result.class)
    @GetMapping(value = "/followup/page")
    public Result patientfollowupPage(@RequestParam(value = "viewerID") Long viewerID,
                                      @RequestParam(value = "startTime", required = false) Date startTime,
                                      @RequestParam(value = "endTime", required = false) Date endTime){
        return manageService.pagingFollowup(viewerID, startTime, endTime);
    }

    @ApiOperation(value = "【医生】忽略随访", response = Result.class)
    @PutMapping(value = "/followup/ignore")
    public Result patientfollowupIgnore(@RequestParam(value = "serialNo") Long serialNo){
        return manageService.ignoreFollowup(serialNo);
    }


    /**
     *  预警
     */
    @ApiOperation(value = "【医生】获取患者预警记录（分页）", response = Result.class)
    @GetMapping(value = "/alert/page")
    public Result patientAlertList(@RequestParam(value = "viewerID") Long viewerID,
                                   @RequestParam(value = "pageIndex") Integer pageIndex,
                                   @RequestParam(value = "pageOffset") Integer pageOffset){
        //患者预警总列表，根据患者id分组并分类
        return manageService.pagingPatientAlert(viewerID, pageIndex, pageOffset);
    }

    @ApiOperation(value = "【医生】获取某患者预警历史", response = Result.class)
    @GetMapping(value = "/alert/list")
    public Result patientAlertPage(@RequestParam(value = "patientID") Long patientID,
                                   @RequestParam(value = "pageIndex") Integer pageIndex,
                                   @RequestParam(value = "pageOffset") Integer pageOffset){
        //单个患者预警记录表
        return manageService.getPatientAlertHist(patientID, pageIndex, pageOffset);
    }

    @ApiOperation(value = "【医生】获取预警患者数量", response = Result.class)
    @GetMapping(value = "/alert/count")
    public Result patientAlertCount(@RequestParam(value = "viewerID") Long viewerID){
        //预警患者的总数量
        return manageService.getAlertPatientCount(viewerID);
    }

    @ApiOperation(value = "【医生】忽略患者预警", response = Result.class)
    @PutMapping(value = "/alert/ignore")
    public Result patientAlertIgnore(@RequestParam(value = "serialNo") Long serialNo,
                                     @RequestParam(value = "ignoreReason", required = false) String ignoreReason,
                                     @RequestParam(value = "executeDoctorID") Long executeDoctorID){
        return manageService.ignoreAlert(serialNo, ignoreReason, executeDoctorID);
    }

    /**
     *  转诊
     */
    @ApiOperation(value = "【医生】获取转入待审核人数", response = Result.class)
    @GetMapping(value = "/referral/count")
    public Result patientRefferalCount(@RequestParam(value = "viewerID") Long viewerID){
        return manageService.getReferralCount(viewerID);
    }

    @ApiOperation(value = "【医生】获取转入待审核列表（分页）", response = Result.class)
    @GetMapping(value = "/referral/page")
    public Result referralReviewCount(@RequestParam(value = "viewerID") Long viewerID){
        return manageService.pagingReferralReview(viewerID);
    }

    @ApiOperation(value = "【医生】转出申请", response = Result.class)
    @PostMapping(value = "/referral/apply")
    public Result referralApply(@RequestBody ReferralApplyDot referralApplyDot){
        return manageService.applyReferral(referralApplyDot);
    }

    @ApiOperation(value = "【医生】转回病人", response = Result.class)
    @PostMapping(value = "/referral/back")
    public Result referralBack(@RequestBody RefferalBackDto refferalBackDto){
        return manageService.backReferral(refferalBackDto);
    }
}
