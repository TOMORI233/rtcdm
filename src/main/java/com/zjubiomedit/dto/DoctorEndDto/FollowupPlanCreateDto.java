package com.zjubiomedit.dto.DoctorEndDto;

import lombok.Data;

import java.util.Date;

@Data
public class FollowupPlanCreateDto {
    private Long patientID;
    private Date planDate;
    private String followUpType;
    private String memo;
}
