package com.zjubiomedit.controller;

import com.zjubiomedit.dto.DoctorEndDto.FollowupPlanCreateDto;
import com.zjubiomedit.dto.DoctorEndDto.FollowupRecordDto;
import com.zjubiomedit.dto.DoctorEndDto.ReferralApplyDto;
import com.zjubiomedit.service.impl.ManageServiceImpl;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiOperation(value = "【医生/医院】获取待审核患者信息（分页）", response = Result.class)
    @GetMapping(value = "/register/page")
    public Result patientRegisterList(@RequestParam(value = "viewerID") Long viewerID,
                                      @RequestParam(value = "pageIndex") Integer pageIndex,
                                      @RequestParam(value = "pageOffset") Integer pageOffset) {
        return manageService.pagingPatientRegister(viewerID, pageIndex, pageOffset);
    }

    @ApiOperation(value = "【医生/医院】审核（通过/拒绝）患者", response = Result.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "doctorID", dataType = "Long", value = "接收患者医生ID", required = true, example = "1"),
            @ApiImplicitParam(name = "status", dataType = "Integer", value = "1-审核通过，2-审核不通过，3-忽略", required = true, defaultValue = "1", example = "1")
    })
    @PostMapping(value = "/register/audit")
    public Result patientRegisterAudit(@RequestParam(value = "serialNo") Long serialNo,
                                       Integer status,
                                       Long doctorID,
                                       @RequestParam(value = "reviewerID") Long reviewerID,
                                       @RequestParam(value = "refuseReason", required = false) String refuseReason) {
        return manageService.reviewRegister(serialNo, status, doctorID, reviewerID, refuseReason);
    }

    /**
     * 管理
     */
    @ApiOperation(value = "【医生】获取个人管理的患者管理索引信息（分页）", response = Result.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "type", dataType = "Integer", value = "0-全部 1-管理中 2-转出 3-转入", required = true, defaultValue = "0", example = "0")})
    @GetMapping(value = "/index/page/doctor")
    public Result doctorManageIndexPage(@RequestParam(value = "doctorID") Long doctorID,
                                        @RequestParam(value = "pageIndex") Integer pageIndex,
                                        @RequestParam(value = "pageOffset") Integer pageOffset,
                                        Integer type) {
        return manageService.pagingDoctorManageIndex(doctorID, pageIndex, pageOffset, type);
    }

    @ApiOperation(value = "【医生/医院】获取某院患者管理索引信息（分页）", response = Result.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "type", dataType = "Integer", value = "0-全部 1-管理中 2-转出 3-转入", required = true, defaultValue = "0", example = "0")})
    @GetMapping(value = "/index/page/hospital")
    public Result hospitalManageIndexPage(@RequestParam(value = "orgCode") String orgCode,
                                          @RequestParam(value = "pageIndex") Integer pageIndex,
                                          @RequestParam(value = "pageOffset") Integer pageOffset,
                                          Integer type) {
        return manageService.pagingHospitalManageIndex(orgCode, pageIndex, pageOffset, type);
    }

    @ApiOperation(value = "【医生】获取个人管理患者总数/管理中人数/转出人数/转入人数", response = Result.class)
    @GetMapping(value = "/index/count/doctor")
    public Result doctorPatientCount(@RequestParam(value = "doctorID") Long doctorID) {
        return manageService.getDoctorPatientCount(doctorID);
    }

    @ApiOperation(value = "【医生/医院】获取某医院管理患者总数/管理中人数/转出人数/转入人数", response = Result.class)
    @GetMapping(value = "/index/count/hospital")
    public Result hospitalPatientCount(@RequestParam(value = "orgCode") String orgCode) {
        return manageService.getHospitalPatientCount(orgCode);
    }

    @ApiOperation(value = "【医生/医院】获取患者ID姓名列表", response = Result.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "type", dataType = "Integer", value = "0-全部 1-管理中 2-转出 3-转入", required = true, defaultValue = "0", example = "0")})
    @GetMapping(value = "/index/patient/namelist")
    public Result doctorPatientList(@RequestParam(value = "viewerID") Long viewerID,
                                    Integer type) {
        return manageService.getPatientList(viewerID, type);
    }

    /**
     * 随访
     */
    @ApiOperation(value = "【医生/医院】获取本院随访总人数/待随访人数/已随访人数/已失效、忽略人数", response = Result.class)
    @GetMapping(value = "/followup/count/this")
    public Result patientFollowupCount(@RequestParam(value = "viewerID") Long viewerID,
                                       @RequestParam(value = "startDate") Date startDate,
                                       @RequestParam(value = "endDate") Date endDate) {
        return manageService.getFollowupCount(viewerID, startDate, endDate);
    }

    @ApiOperation(value = "【医生/医院】获取本院随访列表（分页）", response = Result.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "status", dataType = "Integer", value = "0-待随访 1-已随访 2-已失效/忽略", required = true, defaultValue = "0", example = "0")})
    @GetMapping(value = "/followup/page/this")
    public Result patientFollowupPage(@RequestParam(value = "viewerID") Long viewerID,
                                      Integer status,
                                      @RequestParam(value = "startDate") Date startDate,
                                      @RequestParam(value = "endDate") Date endDate,
                                      @RequestParam(value = "pageIndex") Integer pageIndex,
                                      @RequestParam(value = "pageOffset") Integer pageOffset) {
        return manageService.pagingFollowup(viewerID, status, startDate, endDate, pageIndex, pageOffset);
    }

    @ApiOperation(value = "【医生/医院】获取转诊随访总人数/待随访人数/已随访人数/已失效、忽略人数", response = Result.class)
    @GetMapping(value = "/followup/count/referral")
    public Result ReferralPatientFollowupCount(@RequestParam(value = "viewerID") Long viewerID,
                                               @RequestParam(value = "startDate") Date startDate,
                                               @RequestParam(value = "endDate") Date endDate) {
        return manageService.getReferralFollowupCount(viewerID, startDate, endDate);
    }

    @ApiOperation(value = "【医生/医院】获取转诊随访列表（分页）", response = Result.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "status", dataType = "Integer", value = "0-待随访 1-已随访 2-已失效/忽略", required = true, defaultValue = "0", example = "0")})
    @GetMapping(value = "/followup/page/referral")
    public Result ReferralPatientFollowupPage(@RequestParam(value = "viewerID") Long viewerID,
                                              Integer status,
                                              @RequestParam(value = "startDate") Date startDate,
                                              @RequestParam(value = "endDate") Date endDate,
                                              @RequestParam(value = "pageIndex") Integer pageIndex,
                                              @RequestParam(value = "pageOffset") Integer pageOffset) {
        return manageService.pagingReferralFollowup(viewerID, status, startDate, endDate, pageIndex, pageOffset);
    }

    @ApiOperation(value = "【医生/医院】忽略随访", response = Result.class)
    @PutMapping(value = "/followup/ignore")
    public Result patientFollowupIgnore(@RequestParam(value = "serialNo") Long serialNo) {
        return manageService.ignoreFollowup(serialNo);
    }

    @ApiOperation(value = "【医生/医院】新建随访计划", response = Result.class)
    @PostMapping(value = "/followup/add")
    public Result patientFollowupPlanCreate(@RequestBody FollowupPlanCreateDto followupPlanCreateDto) {
        return manageService.createFollowupPlan(followupPlanCreateDto);
    }

    @ApiOperation(value = "【医生/医院】提交随访记录表", response = Result.class)
    @PostMapping(value = "/followup/record")
    public Result patientFollowupRecord(@RequestBody FollowupRecordDto followupRecordDto) {
        return manageService.recordPatientFollowup(followupRecordDto);
    }

    /**
     * 预警
     */
    @ApiOperation(value = "【医生/医院】获取本院患者预警记录（分页）", response = Result.class)
    @GetMapping(value = "/alert/page/this")
    public Result patientAlertList(@RequestParam(value = "viewerID") Long viewerID,
                                   @RequestParam(value = "pageIndex") Integer pageIndex,
                                   @RequestParam(value = "pageOffset") Integer pageOffset) {
        //患者预警总列表，根据患者id分组并分类
        return manageService.pagingPatientAlert(viewerID, pageIndex, pageOffset);
    }

    @ApiOperation(value = "【医生/医院】获取本院预警患者数量", response = Result.class)
    @GetMapping(value = "/alert/count/this")
    public Result patientAlertCount(@RequestParam(value = "viewerID") Long viewerID) {
        return manageService.getAlertPatientCount(viewerID);
    }

    @ApiOperation(value = "【医生/医院】获取转诊患者预警记录（分页）", response = Result.class)
    @GetMapping(value = "/alert/page/referral")
    public Result ReferralPatientAlertList(@RequestParam(value = "viewerID") Long viewerID,
                                           @RequestParam(value = "pageIndex") Integer pageIndex,
                                           @RequestParam(value = "pageOffset") Integer pageOffset) {
        return manageService.pagingReferralPatientAlert(viewerID, pageIndex, pageOffset);
    }

    @ApiOperation(value = "【医生/医院】获取转诊预警患者数量", response = Result.class)
    @GetMapping(value = "/alert/count/referral")
    public Result ReferralAlertCount(@RequestParam(value = "viewerID") Long viewerID) {
        return manageService.getAlertReferralPatientCount(viewerID);
    }

    @ApiOperation(value = "【医生/医院】忽略患者预警", response = Result.class)
    @PutMapping(value = "/alert/ignore")
    public Result patientAlertIgnore(@RequestParam(value = "serialNo") Long serialNo,
                                     @RequestParam(value = "ignoreReason", required = false) String ignoreReason,
                                     @RequestParam(value = "executeDoctorID") Long executeDoctorID) {
        return manageService.ignoreAlert(serialNo, ignoreReason, executeDoctorID);
    }

    /**
     * 转诊
     */
    @ApiOperation(value = "【医生/医院】获取转入待审核列表（分页）", response = Result.class)
    @GetMapping(value = "/referral/page")
    public Result referralReviewCount(@RequestParam(value = "viewerID") Long viewerID,
                                      @RequestParam(value = "pageIndex") Integer pageIndex,
                                      @RequestParam(value = "pageOffset") Integer pageOffset) {
        return manageService.pagingReferralReview(viewerID, pageIndex, pageOffset);
    }

    @ApiOperation(value = "【医生/医院】拒绝转入", response = Result.class)
    @PutMapping(value = "/referral/refuse")
    public Result referralRefuse(@RequestParam(value = "serialNo") Long serialNo,
                                 @RequestParam(value = "reviewerID") Long reviewerID,
                                 @RequestParam(value = "refuseReason") String refuseReason) {
        return manageService.refuseReferral(serialNo, reviewerID, refuseReason);
    }

    @ApiOperation(value = "【医生/医院】同意转入", response = Result.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "doctorID", value = "接收患者医生ID", dataType = "Long", required = true, example = "1")})
    @PostMapping(value = "/referral/approve")
    public Result referralApprove(@RequestParam(value = "serialNo") Long serialNo,
                                  @RequestParam(value = "reviewerID") Long reviewerID,
                                  Long doctorID) {
        return manageService.approveReferral(serialNo, reviewerID, doctorID);
    }

    @ApiOperation(value = "【医生】转出申请", response = Result.class)
    @PostMapping(value = "/referral/apply")
    public Result referralApply(@RequestBody ReferralApplyDto referralApplyDto) {
        return manageService.applyReferral(referralApplyDto);
    }

    @ApiOperation(value = "【医生】转回病人", response = Result.class)
    @PutMapping(value = "/referral/back")
    public Result referralBack(@RequestParam(value = "serialNo") Long serialNo,
                               @RequestParam(value = "receipt", required = false) String receipt) {
        return manageService.backReferral(serialNo, receipt);
    }
}
