package com.zjubiomedit.dto.DoctorEndDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FollowupCountDto {
    private Long totalCount;
    private Long toFollowupCount;
    private Long followedupCount;
    private Long abolishedCount;
}
