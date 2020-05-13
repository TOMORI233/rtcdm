package com.zjubiomedit.entity.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.annotations.Expose;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class UserBaseEntity {
    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;
    @Expose
    @Column(nullable = false, length = 50)
    private String userName;
    @Column(length = 20)
    private String mobilePhone;
    @Column(length = 50)
    private String email;
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer status = 0; // 0-正常，1-冻结，10-待审核
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @CreatedDate
    private Date registerDateTime;
    @Column(nullable = false)
    private Integer loginCount = 0;
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @LastModifiedDate
    private Date lastLoginDateTime;

}
