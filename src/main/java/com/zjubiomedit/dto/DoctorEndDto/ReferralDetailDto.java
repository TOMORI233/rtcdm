package com.zjubiomedit.dto.DoctorEndDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReferralDetailDto {
    private Long serialNo;
    private Long patientID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reviewDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDateTime;
    private Integer referralType; // 0-上转，1-下转
    private Integer referralPurpose; // 0-住院，1-门诊，2-检查，3-其他
    private String referralReason;
    private String orgCode;
    private Long doctorID;
    private Integer status = 0; // 0-审核中，1-审核通过，2-审核不通过，3-结束转诊
    private Long initiator;
    private Long reviewerID;
    private String refuseReason;
    private String receipt;

    private String targetDoctorName;
    private String targetOrgName;
}
