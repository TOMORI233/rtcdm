package com.zjubiomedit.dto.PagingDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AlertPagingDto {
    // AlertRecord
    private Long patientID;
    //    private Long serialNo;
//    private String alertType;
//    private String alertName;
//    private String alertReason;
//    private String alertMessage;
//    private Date alertTime;
//    private Integer status;
//    private Long followUpSerialNo;
//    private String ignoreReason;
//    private Long executeDoctorID;
//    private Date executeTime;
    private List<AlertUnit> alertUnitList;
    private Integer alertCount;
    // PatientUserBaseInfo
    private String patientName; // name
    private Integer sex;
    private Date dateOfBirth;
    // ManagedPatientIndex
    private Integer manageStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date manageStartDateTime;
    private Long manageDays;
    private Integer manageClass;
    private Integer complianceRate;
    private Integer followupTimes;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastFollowupDate;
    private Long lastFollowupDays;
    // DoctorUserAuths
    private String doctorName; // name
    private String orgCode;
    // OrgDict
    private String orgName;
    // COPDManageDetail
    private Integer manageLevel;
}
