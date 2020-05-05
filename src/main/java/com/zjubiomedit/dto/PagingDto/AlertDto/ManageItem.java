package com.zjubiomedit.dto.PagingDto.AlertDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ManageItem {
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
    // COPDManageDetail
    private Integer manageLevel;
}
