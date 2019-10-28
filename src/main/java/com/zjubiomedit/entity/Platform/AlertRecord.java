package com.zjubiomedit.entity.Platform;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
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
    @CreatedDate
    private Date alertTime;
    @Column(nullable = false)
    private Integer status; // 0-未处理 1-已随访 2-已忽略
    private Integer followUpSerialNo; // Status为1时，这里存对应的随访记录序号。
    private String ignoreReason; // Status为2时有效。忽略预警的原因
    private Long executeDoctorID;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date executeTime;
}
