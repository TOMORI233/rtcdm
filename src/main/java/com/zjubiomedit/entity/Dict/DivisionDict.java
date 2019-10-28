package com.zjubiomedit.entity.Dict;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
public class DivisionDict {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serialNo;
    @Column(length = 14, nullable = false)
    private String divisionCode;
    @Column(length = 70, nullable = false)
    private String divisionName;
    @Column(length = 200, nullable = false)
    private String fullName;
    @Column(length = 14)
    private String parentDivisionCode;
    private Integer itemSortValue;
    private Integer isValid;

}
