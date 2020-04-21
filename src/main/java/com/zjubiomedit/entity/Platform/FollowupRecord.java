package com.zjubiomedit.entity.Platform;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
public class FollowupRecord extends PlatformBaseEntity{

    @Temporal(TemporalType.TIMESTAMP)
    private Date planDate; //常规随访时为计划随访时间,预警干预为预警产生时间
    @Column(length = 100)
    private String followupType; //常规随访/预警干预
    @Column(length = 45)
    private String followupMethod; //例如：电话、门诊、家庭、留言
    @Column(nullable = false)
    private Integer status = 1; // 用于描述本次随访是否有效，0-失访，1-进行中，2-有效，默认值为1
    @Column(length = 100)
    private String failureReason; // 用于描述失访的原因
    @Temporal(TemporalType.TIMESTAMP)
    private Date deathTime; // 只有当失访原因为亡故时才有意义
    @Column(nullable = false)
    private Integer templateCode = 1; // 1-慢阻肺随访记录表, 2-哮喘随访
    @Column(length = 300)
    private String summary;  //  摘要规则根据模板不同而不同。
    @Column(length = 1300)
    private String content;  //  json
    @Column(nullable = false)
    private Long executeDoctorID;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date executeTime; // 实际执行时间
}
