package com.zjubiomedit.dto.DoctorEndDto;

import lombok.Data;

import java.util.Date;

@Data
public class FollowupRecordDto {
    private Long alertSerialNo;
    private Long planSerialNo;
    private Long patientID;
    private Date planDate; //常规随访时为计划随访时间planDate,预警干预为预警产生时间alertTime
    private String followupType; //常规随访/预警干预
    private String followupMethod; //例如：电话、门诊、家庭、留言
    private Integer status; // 用于描述本次随访是否有效，0-失访，1-进行中，2-有效，默认值为1
    private String failureReason; // 用于描述失访的原因
    private Date deathTime; // 只有当失访原因为亡故时才有意义
    private Integer templateCode; // 1-慢阻肺随访记录表, 2-哮喘随访
    private String summary;  //  摘要规则根据模板不同而不同。
    private String content;  //  json
    private Long executeDoctorID;
}
