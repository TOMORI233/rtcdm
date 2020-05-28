package com.zjubiomedit.entity.Platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
@Entity
@Data
public class AlertRecord extends PlatformBaseEntity{

    @Column(nullable = false, length = 30)
    private String alertCode;
    @Column(nullable = false, length = 60)
    private String alertType;
    @Column(nullable = false, length = 100)
    private String alertName;
    @Column(nullable = false, length = 200)
    private String alertReason;
    @Column(length = 1000)
    private String alertMessage;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    private Date alertTime;
    @Column(nullable = false)
    private Integer status; // 0-未处理 1-已随访 2-已转诊 3-已忽略
    private Long followupSerialNo; // Status为1时，这里存对应的随访记录序号。
    private Long referralSerialNo; // Status为2时，这里存对应的转诊记录序号。
    private String ignoreReason; // Status为3时有效。忽略预警的原因
    private Long executeDoctorID;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date executeTime;
}
