package com.zjubiomedit.dto.PagingDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FollowupPagingDto {
    // FollowupPlan
    private Long serialNo;
    private Long patientID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
