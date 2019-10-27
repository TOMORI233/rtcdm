package com.zjubiomedit.entity.User;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class PatientUserBaseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNo;
    @Column(nullable = false)
    private Long userID;
    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false)
    private Integer sex = 0; // 0-未知, 1-男, 2-女，9-未说明
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    @Column(length = 30)
    private String identityCardNumber;
    @Column(length = 30)
    private String education;
    @Column(length = 30)
    private String profession;
    private String photo;
    @Column(length = 20)
    private String phone;
    private String address;
    private Integer height; // 单位：cm
    private Float weight; // 单位：kg

}
