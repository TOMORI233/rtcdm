package com.zjubiomedit.dto.PagingDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class AlertBaseInfo {
    // AlertRecord
    private Long serialNo;
    private Long patientID;
    private String alertType;
    private String alertName;
    private String alertReason;
    private String alertMessage;
    private Date alertTime;
    private Integer status;
    private Long followUpSerialNo;
    private String ignoreReason;
    private Long executeDoctorID;
    private Date executeTime;
    // PatientUserBaseInfo
    private String patientName; // name
    private Integer sex;
    private Date dateOfBirth;
    // ManagedPatientIndex
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
