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
    private Date recordTime; // 记录实际测量时间
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createTime; // 记录insert时间，自动生成

}
