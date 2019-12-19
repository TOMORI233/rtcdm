package com.zjubiomedit.dto.PagingDto;

import lombok.Data;

@Data
public class PagingDto {
    private Long doctorID;
    private Integer pageIndex;
    private Integer pageOffset;
}
