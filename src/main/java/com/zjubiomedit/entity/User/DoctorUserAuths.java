package com.zjubiomedit.entity.User;


import lombok.*;

import javax.persistence.*;

@Entity
@Data
//@Getter
//@Setter
public class DoctorUserAuths extends UserBaseEntity{

    @Column(nullable = false)
    private Integer auth;  // 0-个人账号， 1-集体账号
    @Column(length = 200, nullable = false)
    private String orgCode; // 关联所属机构代码

}
