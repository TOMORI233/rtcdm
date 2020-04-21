package com.zjubiomedit.entity.Platform;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
public class ManagementApplicationReview extends PlatformBaseEntity{

    @Column(length = 45)
    private String applicationFrom;
    @Column(length = 500)
    private String diagnosis;
    @Column(length = 500)
    private String diagnosisMemo;
    @Column(nullable = false)
    private Long hospitalID; //患者注册时指定的医院账号ID
    private Long doctorID; //患者注册时指定的医生个人账号,若未指定在审核时指派
    @Column(nullable = false)
    private Integer status = 0; // 0-未审核，1-审核通过，2-审核不通过，3-忽略。
    private Long reviewerID; //审核人对应的用户ID
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date reviewDateTime;
    private String refuseReason;

}
