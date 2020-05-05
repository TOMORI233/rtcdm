package com.zjubiomedit.dto.PagingDto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AlertUnit {
    private Long serialNo;
    private String alertType;
    private String alertName;
    private String alertReason;
    private String alertMessage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date alertTime;
    private Integer status;
    private Long followUpSerialNo;
    private String ignoreReason;
    private Long executeDoctorID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date executeTime;
}
