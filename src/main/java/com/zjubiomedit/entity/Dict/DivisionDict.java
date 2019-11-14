package com.zjubiomedit.entity.Dict;

import com.google.gson.annotations.Expose;
import lombok.Data;
import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Data
public class DivisionDict {

    @Expose
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNo;
    @Expose
    @Column(length = 14, nullable = false)
    private String divisionCode;
    @Expose
    @Column(length = 70, nullable = false)
    private String divisionName;
    @Column(length = 200, nullable = false)
    private String fullName;
    @Column(length = 14)
    private String parentDivisionCode;
    private Integer itemSortValue;
    private Integer isValid = 1; // 0-无效 1-有效
    @Expose
    @Transient
    private ArrayList<DivisionDict> children;

}
