package com.zjubiomedit.dto.PagingDto;

import lombok.Data;

import java.util.Date;

@Data
public class AlertUnit {
    private Long serialNo;
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
}
