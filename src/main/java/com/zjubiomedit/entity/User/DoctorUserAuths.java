package com.zjubiomedit.entity.User;


import com.google.gson.annotations.Expose;
import lombok.*;
import javax.persistence.*;

@Entity
@Data
public class DoctorUserAuths extends UserBaseEntity{

    @Expose
    @Column(length = 50, nullable = false, columnDefinition = "nvarchar")
    private String name; // 医生姓名"name
    @Column(nullable = false)
    private Integer auth;  // 0-个人账号， 1-集体账号
    @Column(length = 200, nullable = false)
    private String orgCode; // 关联所属机构代码

}
