package com.zjubiomedit.dto.DoctorEndDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatientListDto {
    private Long patientID;
    private String name;
}
