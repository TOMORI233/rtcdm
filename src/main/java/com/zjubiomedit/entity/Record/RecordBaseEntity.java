package com.zjubiomedit.entity.Record;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class RecordBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNo;

    @Column(nullable = false)
    private Long patientID;
    @Column(length = 100)
    private String memo;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date recordTime; // 记录实际测量时间
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(nullable = false)
    private Date createTime; // 记录insert时间，自动生成
    @Column(nullable = false)
    private Integer status = 0; // 是否产生预警标志位，0-未预警，1-已预警
}
