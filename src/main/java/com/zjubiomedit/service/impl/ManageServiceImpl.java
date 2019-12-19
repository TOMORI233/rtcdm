package com.zjubiomedit.service.impl;

import com.zjubiomedit.dao.Platform.ManagementApplicationRepository;
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
    public Result reviewRegister(Long serialNo) {
        //审核
        return new Result();
    }
}
