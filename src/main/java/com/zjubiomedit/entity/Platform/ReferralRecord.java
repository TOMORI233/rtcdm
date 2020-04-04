package com.zjubiomedit.entity.Platform;

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
public class ReferralRecord extends PlatformBaseEntity{
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date startDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date reviewDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateTime;
    @Column(nullable = false)
    private Integer referralType; // 0-上转，1-下转
    @Column(nullable = false)
    private Integer referralPurpose; // 0-住院，1-门诊，2-检查，3-其他
    @Column(length = 255)
    private String referralReason;
    @Column(nullable = false, length = 30)
    private String orgCode;
    private Long doctorID;
    @Column(nullable = false)
    private Integer status; // 0-审核中，1-审核通过，2-审核不通过，3-结束转诊
    @Column(nullable = false, length = 20)
    private String initiator;
    @Column(length = 100)
    private String refuseReason;
}
