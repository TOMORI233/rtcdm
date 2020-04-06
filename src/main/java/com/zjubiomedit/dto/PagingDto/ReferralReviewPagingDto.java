package com.zjubiomedit.dto.PagingDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ReferralReviewPagingDto {
    // ReferralRecord
    private Long serialNo;
    private Long patientID;
    private Integer referralType;
    private Integer referralPurpose;
    private String referralReason;
    private Long targetDoctorID; // doctorID
    private Date startDateTime;
    // PatientUserBaseInfo
    private String patientName; // name
    private Integer sex;
    private Date dateOfBirth;
    // ManagedPatientIndex
    private Integer manageStatus;
    private Integer complianceRate;
    // DoctorUserAuths
    private String targetDoctorName; // name
    private String linkedDoctorName; // name
    private String linkedOrgCode;
    // OrgDict
    private String linkedOrgName;
    // COPDManageDetail
    private Integer manageLevel;
}
