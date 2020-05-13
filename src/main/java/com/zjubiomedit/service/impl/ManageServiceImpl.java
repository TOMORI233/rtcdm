package com.zjubiomedit.service.impl;

import com.zjubiomedit.config.exception.CommonJsonException;
import com.zjubiomedit.dao.Platform.*;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.dao.User.PatientUserAuthsRepository;
import com.zjubiomedit.dto.DoctorEndDto.*;
import com.zjubiomedit.dto.PagingDto.*;
import com.zjubiomedit.dto.PagingDto.AlertDto.AlertBaseInfo;
import com.zjubiomedit.dto.PagingDto.AlertDto.AlertPagingDto;
import com.zjubiomedit.dto.PagingDto.AlertDto.AlertItem;
import com.zjubiomedit.dto.PagingDto.AlertDto.ManageItem;
import com.zjubiomedit.entity.Platform.*;
import com.zjubiomedit.entity.User.PatientUserAuths;
import com.zjubiomedit.service.ManageService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
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
    FollowupRecordRepository followupRecordRepository;
    @Autowired
    FollowupPlanRepository followupPlanRepository;
    @Autowired
    ReferralRecordRepository referralRecordRepository;

    @Override
    public Result pagingPatientRegister(Long viewerID, Integer pageIndex, Integer pageOffset) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
            Page<RegisterPagingDto> page = managementApplicationRepository.findByViewerID(viewerID, pageable);
            return new Result(page);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result reviewRegister(Long serialNo, Integer status, Long doctorID, Long reviewerID, String refuseReason) {
        //审核
        if (status == Utils.REVIEW_APPROVED && doctorID == null) {
            return new Result(ErrorEnum.E_501);
        }
        try {
            Optional<ManagementApplicationReview> registerReview = managementApplicationRepository.findBySerialNo(serialNo);
            if (registerReview.isPresent()) {
                ManagementApplicationReview review = registerReview.get();
                review.setReviewerID(reviewerID);
                Long patientID = review.getPatientID();
                Long hospitalID = review.getHospitalID();
                Date dt = new Date();
                if (review.getStatus().equals(Utils.REVIEW_APPROVED) || review.getStatus().equals(Utils.REVIEW_FAILED)) {
                    return new Result(ErrorEnum.E_30000);
                }
                if (status.equals(Utils.REVIEW_APPROVED)) { //通过
                    review.setDoctorID(doctorID);
                    // ManagedPatientIndex表添加患者医生关系
                    ManagedPatientIndex newIndex = new ManagedPatientIndex();
                    newIndex.setPatientID(patientID);
                    newIndex.setHospitalID(hospitalID);
                    newIndex.setDoctorID(doctorID);
                    newIndex.setManageStartDateTime(dt);
                    managedPatientIndexRepository.save(newIndex);
                    // 修改PatientUserAuths表用户状态
                    Optional<PatientUserAuths> thisPatientOptional = patientUserAuthsRepository.findByUserID(patientID);
                    thisPatientOptional.ifPresent(thisPatient->{
                        thisPatient.setStatus(Utils.USER_ACTIVE);
                        patientUserAuthsRepository.save(thisPatient);
                    });
                    // COPDManageDetail建立患者管理等级
                    COPDManageDetail thisManage = new COPDManageDetail();
                    thisManage.setPatientID(patientID);
                    copdManageDetailRepository.save(thisManage);
                } else if (status == Utils.REVIEW_FAILED) { //不通过
                    review.setRefuseReason(refuseReason);
                }
                review.setStatus(status);
                managementApplicationRepository.save(review);
                return new Result();
            } else {
                return new Result(ErrorEnum.E_10008);
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result pagingPatientAlert(Long viewerID, Integer pageIndex, Integer pageOffset) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "alertTime");
            List<AlertBaseInfo> baseList = alertRecordRepository.findAlertPageByViewerIDAndStatus(viewerID, Utils.ALERT_UNPROCESSED);
            HashMap<Long, AlertPagingDto> map = new HashMap<>();
            baseList.forEach(baseInfo -> {
                if (map.containsKey(baseInfo.getPatientID())) {
                    AlertItem newItem = new AlertItem();
                    BeanUtils.copyProperties(baseInfo, newItem);
                    map.get(baseInfo.getPatientID()).getAlertItemList().add(newItem);
                } else {
                    AlertPagingDto newPat = new AlertPagingDto();
                    ManageItem manageItem = new ManageItem();
                    BeanUtils.copyProperties(baseInfo, newPat);
                    BeanUtils.copyProperties(baseInfo, manageItem);
                    newPat.setManageItem(manageItem);
                    AlertItem newItem = new AlertItem();
                    BeanUtils.copyProperties(baseInfo, newItem);
                    List<AlertItem> newList = new LinkedList<>();
                    newList.add(newItem);
                    newPat.setAlertItemList(newList);
                    map.put(baseInfo.getPatientID(), newPat);
                }
            });
            List<AlertPagingDto> pageList = new ArrayList<>(map.values());
            int fromIndex = pageOffset * (pageIndex - 1);
            int toIndex = pageList.size();
            if (fromIndex > pageList.size() - 1) { // 页面范围超出数据范围
                return new Result(ErrorEnum.E_501);
            } else if (toIndex >= pageOffset * pageIndex) { // fromIndex包括, toIndex不包括
                toIndex = pageOffset * pageIndex;
            }
            List<AlertPagingDto> indexPage = pageList.subList(fromIndex, toIndex);
            indexPage.forEach(each -> {
                each.setAlertCount(each.getAlertItemList().size());
                Date date = new Date();
                // 根据时间计算距今天数
                if (each.getManageItem().getManageStartDateTime() != null) {
                    each.getManageItem().setManageDays((date.getTime() - each.getManageItem().getManageStartDateTime().getTime()) / 86400000);
                }
                if (each.getManageItem().getLastFollowupDate() != null) {
                    each.getManageItem().setLastFollowupDays((date.getTime() - each.getManageItem().getLastFollowupDate().getTime()) / 86400000);
                }
            });
            Page<AlertPagingDto> page = new PageImpl<>(indexPage, pageable, pageList.size());
            return new Result(page);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getAlertPatientCount(Long viewerID) {
        try {
            return new Result(alertRecordRepository.CountPatientByViewerID(viewerID));
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result ignoreAlert(Long serialNo, String ignoreReason, Long executeDoctorID) {
        try {
            Optional<AlertRecord> alertRecordOptional = alertRecordRepository.findBySerialNoAndStatus(serialNo, Utils.ALERT_UNPROCESSED);
            Result result = new Result(ErrorEnum.E_10008);
            alertRecordOptional.ifPresent(alertRecord -> {
                alertRecord.setIgnoreReason(ignoreReason);
                alertRecord.setStatus(Utils.ALERT_IGNORED);
                alertRecord.setExecuteDoctorID(executeDoctorID);
                alertRecordRepository.save(alertRecord);
                result.setCode(0);
                result.setMessage("success");
            });
            return result;
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result pagingReferralReview(Long viewerID, Integer pageIndex, Integer pageOffset) {
        try {
            Optional<Integer> auth = doctorUserAuthsRepository.findAuthById(viewerID);
            if (auth.isPresent()) {
                Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
                Page<ReferralReviewPagingDto> page;
                if (auth.get().equals(Utils.GROUP)) {
                    page = referralRecordRepository.findReferralReviewPageByHospitalID(viewerID, pageable);
                } else {
                    page = referralRecordRepository.findReferralReviewPageByDoctorID(viewerID, pageable);
                }
                return new Result(page);
            } else {
                return new Result(ErrorEnum.E_10008);
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result applyReferral(ReferralApplyDto referralApplyDto) {
        try {
            ReferralRecord newRecord = new ReferralRecord();
            BeanUtils.copyProperties(referralApplyDto, newRecord);
            referralRecordRepository.save(newRecord);
            return new Result();
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result getFollowupCount(Long viewerID, Date startDate, Date endDate) {
        try {
            Long toFollowupCount = followupPlanRepository.CountByViewerIDAndStatusAndDate(viewerID, Utils.FOLLOW_PLAN_TODO, startDate, endDate);
            Long followedupCount = followupPlanRepository.CountByViewerIDAndStatusAndDate(viewerID, Utils.FOLLOW_PLAN_FINISHED, startDate, endDate);
            Long abolishedCount = followupPlanRepository.CountByViewerIDAndStatusAndDate(viewerID, Utils.FOLLOW_PLAN_ABOLISHED, startDate, endDate);
            Long totalCount = toFollowupCount + followedupCount + abolishedCount;
            FollowupCountDto followupCountDto = new FollowupCountDto(totalCount, toFollowupCount, followedupCount, abolishedCount);
            return new Result(followupCountDto);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result pagingFollowup(Long viewerID, Integer status, Date startDate, Date endDate, Integer pageIndex, Integer pageOffset) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.ASC, "planDate");
            Page<FollowupPagingDto> page = followupPlanRepository.findFollowupPageByViewerIDAndStatusAndTime(viewerID, status, startDate, endDate, pageable);
            return new Result(page);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result ignoreFollowup(Long serialNo) {
        try {
            Optional<FollowupPlan> thisPlanOptional = followupPlanRepository.findBySerialNoAndStatus(serialNo, Utils.FOLLOW_PLAN_TODO);
            Result result = new Result(ErrorEnum.E_10008);
            thisPlanOptional.ifPresent(thisPlan -> {
                thisPlan.setStatus(Utils.FOLLOW_PLAN_ABOLISHED);
                followupPlanRepository.save(thisPlan);
                result.setCode(0);
                result.setMessage("success");
            });
            return result;
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result pagingDoctorManageIndex(Long doctorID, Integer pageIndex, Integer pageOffset, Integer type) {
        try {
            Optional<Integer> auth = doctorUserAuthsRepository.findAuthById(doctorID);
            if (auth.isPresent()) {
                if (auth.get().equals(Utils.GROUP)) {
                    return new Result(ErrorEnum.E_401);
                }
                Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset);
                // 按patientID排序，否则分页错误
                switch (type) {
                    case Utils.TYPE_ALL:
                        Page<ManageIndexPagingDto> pageAll = managedPatientIndexRepository.findAllManageIndexByDoctorID(doctorID, pageable);
                        return new Result(pageAll);
                    case Utils.TYPE_MANAGING:
                        Page<ManageIndexPagingDto> pageManaging = managedPatientIndexRepository.findManagingManageIndexByDoctorID(doctorID, pageable);
                        return new Result(pageManaging);
                    case Utils.TYPE_REFERRAL_OUT:
                        Page<ManageIndexPagingDto> pageReferralOut = managedPatientIndexRepository.findReferralOutManageIndexByDoctorID(doctorID, pageable);
                        return new Result(pageReferralOut);
                    case Utils.TYPE_REFERRAL_IN:
                        Page<ManageIndexPagingDto> pageReferralIn = managedPatientIndexRepository.findReferralInManageIndexByDoctorID(doctorID, pageable);
                        return new Result(pageReferralIn);
                    default:
                        return new Result(ErrorEnum.E_501);
                }
            } else {
                return new Result(ErrorEnum.E_10008);
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }

    }

    @Override
    public Result pagingHospitalManageIndex(String orgCode, Integer pageIndex, Integer pageOffset, Integer type) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset);
            // 按patientID排序，否则分页错误
            switch (type) {
                case Utils.TYPE_ALL:
                    Page<ManageIndexPagingDto> pageAll = managedPatientIndexRepository.findAllManageIndexByOrgCode(orgCode, pageable);
                    return new Result(pageAll);
                case Utils.TYPE_MANAGING:
                    Page<ManageIndexPagingDto> pageManaging = managedPatientIndexRepository.findManagingManageIndexByOrgCode(orgCode, pageable);
                    return new Result(pageManaging);
                case Utils.TYPE_REFERRAL_OUT:
                    Page<ManageIndexPagingDto> pageReferralOut = managedPatientIndexRepository.findReferralOutManageIndexByOrgCode(orgCode, pageable);
                    return new Result(pageReferralOut);
                case Utils.TYPE_REFERRAL_IN:
                    Page<ManageIndexPagingDto> pageReferralIn = managedPatientIndexRepository.findReferralInManageIndexByOrgCode(orgCode, pageable);
                    return new Result(pageReferralIn);
                default:
                    return new Result(ErrorEnum.E_501);
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getDoctorPatientCount(Long doctorID) {
        try {
            Optional<Integer> auth = doctorUserAuthsRepository.findAuthById(doctorID);
            if (auth.isPresent()) {
                if (auth.get().equals(Utils.GROUP)) {
                    return new Result(ErrorEnum.E_401);
                }
                Long managingCount = managedPatientIndexRepository.CountManagingByDoctorID(doctorID);
                Long referralOutCount = managedPatientIndexRepository.CountReferralOutByDoctorID(doctorID);
                Long referralInCount = managedPatientIndexRepository.CountReferralInByDoctorID(doctorID);
                Long totalCount = managingCount + referralOutCount + referralInCount;
                ManagedPatientCountDto patientCountDto = new ManagedPatientCountDto(totalCount, managingCount, referralOutCount, referralInCount);
                return new Result(patientCountDto);
            } else {
                return new Result(ErrorEnum.E_400);
            }
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getHospitalPatientCount(String orgCode) {
        try {
            Long managingCount = managedPatientIndexRepository.CountManagingByOrgCode(orgCode);
            Long referralOutCount = managedPatientIndexRepository.CountReferralOutByOrgCode(orgCode);
            Long referralInCount = managedPatientIndexRepository.CountReferralInByOrgCode(orgCode);
            Long totalCount = managingCount + referralOutCount + referralInCount;
            ManagedPatientCountDto patientCountDto = new ManagedPatientCountDto(totalCount, managingCount, referralOutCount, referralInCount);
            return new Result(patientCountDto);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getPatientList(Long viewerID, Integer type) {
        try {
            List<PatientListDto> patientList = managedPatientIndexRepository.findPatientByViewerIDAndType(viewerID, type);
            return new Result(patientList);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result createFollowupPlan(FollowupPlanCreateDto followupPlanCreateDto) {
        try {
            FollowupPlan thisPlan = new FollowupPlan();
            thisPlan.setPatientID(followupPlanCreateDto.getPatientID());
            thisPlan.setPlanDate(followupPlanCreateDto.getPlanDate());
            thisPlan.setFollowupType(followupPlanCreateDto.getFollowupType());
            thisPlan.setMemo(followupPlanCreateDto.getMemo());
            thisPlan.setStatus(Utils.FOLLOW_PLAN_TODO);
            followupPlanRepository.save(thisPlan);
            return new Result();
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result refuseReferral(Long serialNo, Long reviewerID, String refuseReason) {
        try {
            Optional<ReferralRecord> thisRecord = referralRecordRepository.findBySerialNo(serialNo);
            thisRecord.ifPresent(record -> {
                record.setReviewerID(reviewerID);
                record.setStatus(Utils.REFERRAL_FAILED);
                record.setReviewDateTime(new Date());
                record.setRefuseReason(refuseReason);
                referralRecordRepository.save(record);
            });
            return new Result();
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result approveReferral(Long serialNo, Long reviewerID, Long doctorID) {
        try {
            Optional<ReferralRecord> thisRecord = referralRecordRepository.findBySerialNo(serialNo);
            thisRecord.ifPresent(record -> {
                if (record.getStatus().equals(Utils.REFERRAL_UNREVIEWED)) {
                    record.setReviewerID(reviewerID);
                    record.setDoctorID(doctorID);
                    record.setStatus(Utils.REFERRAL_APPROVED);
                    record.setReviewDateTime(new Date());
                    Optional<ManagedPatientIndex> thisPatientOptional = managedPatientIndexRepository.findByPatientID(record.getPatientID());
                    thisPatientOptional.ifPresent(thisPatient -> {
                        thisPatient.setManageStatus(record.getReferralType());
                        referralRecordRepository.save(record);
                        managedPatientIndexRepository.save(thisPatient);
                    });
                }
            });
            return new Result();
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result backReferral(Long serialNo, String receipt) {
        try {
            Optional<ReferralRecord> thisRecord = referralRecordRepository.findBySerialNo(serialNo);
            Result result = new Result(ErrorEnum.E_10008);
            thisRecord.ifPresent(record -> {
                if (record.getStatus().equals(Utils.REFERRAL_APPROVED)) {
                    record.setStatus(Utils.REFERRAL_OVER);
                    record.setEndDateTime(new Date());
                    record.setReceipt(receipt);
                    Optional<ManagedPatientIndex> thisPatientOptional = managedPatientIndexRepository.findByPatientID(record.getPatientID());
                    thisPatientOptional.ifPresent(thisPatient -> {
                        thisPatient.setManageStatus(Utils.MANAGE_PROCESSING);
                        referralRecordRepository.save(record);
                        managedPatientIndexRepository.save(thisPatient);
                        result.setCode(0);
                        result.setMessage("success");
                    });
                }
            });
            return result;
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result recordPatientFollowup(FollowupRecordDto followupRecordDto) {
        try {
            Optional<ManagedPatientIndex> indexOptional = managedPatientIndexRepository.findByPatientID(followupRecordDto.getPatientID());
            Result result = new Result(ErrorEnum.E_10008);
            indexOptional.ifPresent(patientIndex -> {
                patientIndex.setFollowupTimes(patientIndex.getFollowupTimes() + 1);
                patientIndex.setLastFollowupDate(new Date());
                managedPatientIndexRepository.save(patientIndex);
                FollowupRecord newFollowupRecord = new FollowupRecord();
                BeanUtils.copyProperties(followupRecordDto, newFollowupRecord);
                FollowupRecord thisFollowup = followupRecordRepository.save(newFollowupRecord);
                if (followupRecordDto.getAlertSerialNo() != null) {
                    Optional<AlertRecord> thisAlertOptional = alertRecordRepository.findBySerialNoAndStatus(followupRecordDto.getAlertSerialNo(), Utils.ALERT_UNPROCESSED);
                    thisAlertOptional.ifPresent(thisAlert -> {
                        thisAlert.setExecuteDoctorID(followupRecordDto.getExecuteDoctorID());
                        thisAlert.setFollowUpSerialNo(thisFollowup.getSerialNo());
                        thisAlert.setStatus(Utils.ALERT_FOLLOWEDUP);
                        thisFollowup.setPlanDate(thisAlert.getAlertTime());
                        followupRecordRepository.save(thisFollowup);
                        alertRecordRepository.save(thisAlert);
                    });
                }
                if (followupRecordDto.getPlanSerialNo() != null) {
                    Optional<FollowupPlan> thisPlanOptional = followupPlanRepository.findBySerialNoAndStatus(followupRecordDto.getPlanSerialNo(), Utils.FOLLOW_PLAN_TODO);
                    thisPlanOptional.ifPresent(thisPlan -> {
                        thisPlan.setStatus(Utils.FOLLOW_PLAN_FINISHED);
                        thisFollowup.setPlanDate(thisPlan.getPlanDate());
                        followupRecordRepository.save(thisFollowup);
                        followupPlanRepository.save(thisPlan);
                    });
                }
                result.setCode(0);
                result.setMessage("success");
            });
            return result;
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10005);
        }
    }

    @Override
    public Result pagingReferralPatientAlert(Long viewerID, Integer pageIndex, Integer pageOffset) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "alertTime");
            List<AlertBaseInfo> baseList = alertRecordRepository.findReferralAlertPageByViewerIDAndStatus(viewerID, Utils.ALERT_UNPROCESSED);
            HashMap<Long, AlertPagingDto> map = new HashMap<>();
            baseList.forEach(baseInfo -> {
                if (map.containsKey(baseInfo.getPatientID())) {
                    AlertItem newItem = new AlertItem();
                    BeanUtils.copyProperties(baseInfo, newItem);
                    map.get(baseInfo.getPatientID()).getAlertItemList().add(newItem);
                } else {
                    AlertPagingDto newPat = new AlertPagingDto();
                    ManageItem manageItem = new ManageItem();
                    BeanUtils.copyProperties(baseInfo, newPat);
                    BeanUtils.copyProperties(baseInfo, manageItem);
                    newPat.setManageItem(manageItem);
                    AlertItem newItem = new AlertItem();
                    BeanUtils.copyProperties(baseInfo, newItem);
                    List<AlertItem> newList = new LinkedList<>();
                    newList.add(newItem);
                    newPat.setAlertItemList(newList);
                    map.put(baseInfo.getPatientID(), newPat);
                }
            });
            List<AlertPagingDto> pageList = new ArrayList<>(map.values());
            int fromIndex = pageOffset * (pageIndex - 1);
            int toIndex = pageList.size();
            if (fromIndex > pageList.size() - 1) { // 页面范围超出数据范围
                return new Result(ErrorEnum.E_501);
            } else if (toIndex >= pageOffset * pageIndex) { // fromIndex包括, toIndex不包括
                toIndex = pageOffset * pageIndex;
            }
            List<AlertPagingDto> indexPage = pageList.subList(fromIndex, toIndex);
            indexPage.forEach(each -> {
                each.setAlertCount(each.getAlertItemList().size());
                Date date = new Date();
                if (each.getManageItem().getManageStartDateTime() != null) {
                    each.getManageItem().setManageDays((date.getTime() - each.getManageItem().getManageStartDateTime().getTime()) / 86400000);
                }
                if (each.getManageItem().getLastFollowupDate() != null) {
                    each.getManageItem().setLastFollowupDays((date.getTime() - each.getManageItem().getLastFollowupDate().getTime()) / 86400000);
                }
            });
            Page<AlertPagingDto> page = new PageImpl<>(indexPage, pageable, pageList.size());
            return new Result(page);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getAlertReferralPatientCount(Long viewerID) {
        try {
            return new Result(alertRecordRepository.CountReferralPatientByViewerID(viewerID));
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result getReferralFollowupCount(Long viewerID, Date startDate, Date endDate) {
        try {
            Long toFollowupCount = followupPlanRepository.CountReferralByViewerIDAndStatusAndDate(viewerID, Utils.FOLLOW_PLAN_TODO, startDate, endDate);
            Long followedupCount = followupPlanRepository.CountReferralByViewerIDAndStatusAndDate(viewerID, Utils.FOLLOW_PLAN_FINISHED, startDate, endDate);
            Long abolishedCount = followupPlanRepository.CountReferralByViewerIDAndStatusAndDate(viewerID, Utils.FOLLOW_PLAN_ABOLISHED, startDate, endDate);
            Long totalCount = toFollowupCount + followedupCount + abolishedCount;
            FollowupCountDto followupCountDto = new FollowupCountDto(totalCount, toFollowupCount, followedupCount, abolishedCount);
            return new Result(followupCountDto);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

    @Override
    public Result pagingReferralFollowup(Long viewerID, Integer status, Date startDate, Date endDate, Integer pageIndex, Integer pageOffset) {
        try {
            Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.ASC, "planDate");
            Page<FollowupPagingDto> page = followupPlanRepository.findReferralFollowupPageByViewerIDAndStatusAndTime(viewerID, status, startDate, endDate, pageable);
            return new Result(page);
        } catch (NullPointerException e) {
            throw new CommonJsonException(ErrorEnum.E_10007);
        }
    }

}
