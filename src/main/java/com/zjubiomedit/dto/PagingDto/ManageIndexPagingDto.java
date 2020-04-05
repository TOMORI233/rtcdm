package com.zjubiomedit.dto.PagingDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class ManageIndexPagingDto {
    // PatientUserBaseInfo
    private String patientName; // name
    private Integer sex;
    private Date dateOfBirth;
    // ManagedPatientIndex
    private Long patientID;
    private Integer manageStatus;
    private Integer complianceRate;
    private Date manageStartDateTime;
    private Date terminationDateTime;
    // DoctorUserAuths
    private String doctorName; // name
    private String orgCode;
    // OrgDict
    private String orgName;
    // COPDManageDetail
    private Integer manageLevel;
}
