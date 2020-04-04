package com.zjubiomedit.dto.PagingDto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class RegisterPagingDto {
    //PatientUserBaseInfo
    private String name;
    private Integer sex;
    private Date dateOfBirth;
    private String profession;
    private String education;
    private Integer height;
    private Float weight;
    //PatientUserAuths
    private Date registerDateTime;
    //ManagementApplicationReview
    private Long serialNo;
    private Long doctorID;
    private String diagnosis;
}
