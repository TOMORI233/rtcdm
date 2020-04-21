package com.zjubiomedit.entity.Dict;

import com.google.gson.annotations.Expose;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OrgDict {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNo;

    @Expose
    @Column(length = 14, nullable = false)
    private String orgCode;
    @Expose
    @Column(length = 70, nullable = false)
    private String orgName;
    private Integer hospitalClass; // 0-未知级别; 1-一级丙等; 2-一级乙等; 3-一级甲等; 4-二级丙等; 5-二级乙等; 6-二级甲等; 7-三级丙等; 8-三级乙等; 9-三级甲等;
    private Integer hospitalDivisionClass; // 0-省级，1-市级，2-区、县，3-低于县级（例如卫生院）
    @Column(length = 14)
    private String parentOrgCode;
    @Column(length = 14)
    private String divisionCode;
    @Column(length = 100)
    private String address;
    @Column(length = 100)
    private String memo;
    private Integer itemSortValue;
    @Column(nullable = false)
    private Integer isValid = 1;

}
