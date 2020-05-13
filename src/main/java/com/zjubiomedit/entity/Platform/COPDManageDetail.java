package com.zjubiomedit.entity.Platform;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Data
public class COPDManageDetail extends PlatformBaseEntity{
    @Column(nullable = false)
    private Integer manageLevel = 0; // 当前管理等级，0-新患者，1-一级管理，2-二级管理，9-终止管理
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    private Date manageLevelStartDateTime;
}
