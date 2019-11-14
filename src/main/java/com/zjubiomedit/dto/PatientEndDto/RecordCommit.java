package com.zjubiomedit.dto.PatientEndDto;

import lombok.Data;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-05
 */
@Data
public class RecordCommit {
    private int type;
    //JSON.STRINGIFY 产生的，根据不同的type解析为不同的record entity
    private String data;
}
