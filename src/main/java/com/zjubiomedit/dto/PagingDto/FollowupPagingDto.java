package com.zjubiomedit.dto.PagingDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FollowupPagingDto {
    // FollowupPlan
    private Long serialNo;
    private Long patientID;
    private Date planDate;
    private Integer status;
    private String followUpType;
    private String memo;
    // PatientUserBaseInfo
    private String patientName; // name
    private Integer sex;
    private Date dateOfBirth;
    // ManagedPatientIndex
    private Integer followupTimes;
    private Date lastFollowupDate;
    private Integer manageStatus;
    private Integer complianceRate;
    // DoctorUserAuths
    private String doctorName; // name
    private String orgCode;
    // OrgDict
    private String orgName;
    // COPDManageDetail
    private Integer manageLevel;
}
