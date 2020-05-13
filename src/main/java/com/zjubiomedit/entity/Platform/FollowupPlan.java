package com.zjubiomedit.entity.Platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
public class FollowupPlan extends PlatformBaseEntity {
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planDate;
    @Column(nullable = false, length = 100)
    private String followupType;
    @Column(nullable = false)
    private Integer status = 0;
    @Column(length = 100)
    private String memo;
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateTime;
}
