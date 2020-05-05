package com.zjubiomedit.dto.PagingDto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDateTime;
    // PatientUserBaseInfo
    private String patientName; // name
    private Integer sex;
    private Date dateOfBirth;
    // ManagedPatientIndex
    private Integer manageStatus;
    private Integer complianceRate;
    // DoctorUserAuths
    private String linkedDoctorName; // name
    private String linkedOrgCode;
    // OrgDict
    private String linkedOrgName;
    // COPDManageDetail
    private Integer manageLevel;
}
