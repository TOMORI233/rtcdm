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
public class ManagementPlan extends PlatformBaseEntity{
    @Column(length = 45, nullable = false)
    private String planType;
    @Column(length = 45)
    private String planName;
    @Column(length = 1500)
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date planStartDateTime;
    private Long plannerID;
    @Column(length = 45, columnDefinition = "nvarchar")
    private String plannerName;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date LastModifyDateTime;
    private Long modifierID;
    @Column(length = 45, columnDefinition = "nvarchar")
    private String modifierName;
    private Integer status; //0-未开始，1-使用中，2-已作废

}
