package com.zjubiomedit.dto.DoctorEndDto;

import lombok.Data;

@Data
public class ReferralApplyDto {
    private Long patientID;
    private Integer referralType;
    private Integer referralPurpose;
    private String referralReason;
    private String orgCode;
    private Long doctorID;
    private Long initiatorID;
}
