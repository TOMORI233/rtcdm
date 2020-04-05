package com.zjubiomedit.dto.DoctorEndDto;

import lombok.Data;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-05
 */
@Data
public class DoctorUserLoginDto {
    private String userName;
    private Integer auth;
    private String orgCode;
}
