package com.zjubiomedit.service.impl;

import com.zjubiomedit.dao.Platform.ManagedPatientIndexRepository;
import com.zjubiomedit.dao.Platform.ManagementApplicationRepository;
import com.zjubiomedit.dto.DoctorEndDto.ReviewRegisterDto;
import com.zjubiomedit.dto.PagingDto.PagingDto;
import com.zjubiomedit.entity.Platform.ManagementApplicationReview;
import com.zjubiomedit.service.ManageService;
import com.zjubiomedit.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ManageServiceImpl implements ManageService {

    @Autowired
    ManagementApplicationRepository managementApplicationRepository;
    @Autowired
    ManagedPatientIndexRepository managedPatientIndexRepository;

    @Override
    public Page<ManagementApplicationReview> pagingPatientRegister(PagingDto pagingDto) {
        Long doctorID = pagingDto.getDoctorID();
        Integer pageIndex = pagingDto.getPageIndex();
        Integer pageOffset = pagingDto.getPageOffset();
        Pageable pageable = PageRequest.of(pageIndex - 1, pageOffset, Sort.Direction.DESC, "serialNo");
        Page<ManagementApplicationReview> page = managementApplicationRepository.findByDoctorID(doctorID, pageable);
        return page;
    }

    @Override
    public Result reviewRegister(ReviewRegisterDto reviewRegisterDto) {
//        //审核
//        Optional<ManagementApplicationReview> registerRecord = managementApplicationRepository.findBySerialNo(serialNo);
//        if(registerRecord.isPresent()){
//            //审核通过
//            //将该患者信息导入ManagedPatientIndex列表
//            managedPatientIndexRepository.save();
//            //PatientUserAuths表中该患者status置0
//
//            //将该申请记录从ManagementApplicationReview表中删除
//
//        }
//        else{
//            return new Result("记录不存在"); //枚举类待存
//        }
//        return new Result();
        return null;
    }
}
