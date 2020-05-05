package com.zjubiomedit.dto.PagingDto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date registerDateTime;
    //ManagementApplicationReview
    private Long serialNo;
    private Long doctorID;
    private String diagnosis;
}
