package com.zjubiomedit.dto.DoctorEndDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagedPatientCountDto {
    private Long totalCount;
    private Long managingCount;
    private Long referralOutCount;
    private Long referralInCount;
}
