package com.zjubiomedit.dto.PagingDto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date manageStartDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date terminationDateTime;
    // DoctorUserAuths
    private String doctorName; // name
    private String orgCode;
    // OrgDict
    private String orgName;
    // COPDManageDetail
    private Integer manageLevel;
}
