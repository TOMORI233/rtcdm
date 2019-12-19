package com.zjubiomedit.service;

import com.zjubiomedit.dto.DoctorEndDto.ReviewRegisterDto;
import com.zjubiomedit.dto.PagingDto.PagingDto;
import com.zjubiomedit.entity.Platform.ManagementApplicationReview;
import com.zjubiomedit.util.Result;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ManageService {
    /**
     *  审核患者分页
     * @return
     */
    Page<ManagementApplicationReview> pagingPatientRegister(PagingDto pagingDto);

    /**
     *  审核患者
     */
    Result reviewRegister(ReviewRegisterDto reviewRegisterDto);
}
