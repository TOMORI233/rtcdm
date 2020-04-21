package com.zjubiomedit.util.enums;

public class AlertEnum {
    //ALERT CODE
    public static final String CODE_REF = "REFERRAL";
    public static final String CODE_CAT = "CATWarning";
    public static final String CODE_PEF = "PEFWarning";
    public static final String CODE_DIS = "DISCOMFORT";
    public static final String CODE_ANX = "ANXIETY";
    public static final String CODE_DEP = "DEPRESSION";
    //ALERT TYPE
    public static final String TYPE_REF = "CAT&PEF&HAD";
    public static final String TYPE_CAT = "CAT";
    public static final String TYPE_PEF = "PEF";
    public static final String TYPE_DIS = "discomfort";
    public static final String TYPE_HAD = "HAD";
    //ALERT NAME
    public static final String NAME_REF_1 = "转诊一级预警";
    public static final String NAME_REF_2 = "转诊二级预警";
    public static final String NAME_CAT_1 = "CAT轻度预警";
    public static final String NAME_CAT_2 = "CAT中度预警";
    public static final String NAME_CAT_3 = "CAT重度预警";
    public static final String NAME_PEF_1 = "PEF轻度预警";
    public static final String NAME_PEF_2 = "PEF重度预警";
    public static final String NAME_DIS = "不适预警";
    public static final String NAME_ANX_1 = "焦虑轻度预警";
    public static final String NAME_ANX_2 = "焦虑中度预警";
    public static final String NAME_ANX_3 = "焦虑重度预警";
    public static final String NAME_DEP_1 = "抑郁轻度预警";
    public static final String NAME_DEP_2 = "抑郁中度预警";
    public static final String NAME_DEP_3 = "抑郁重度预警";
    //ALERT MESSAGE
    public static final String MSG_REF = "转诊预警，患者可能需要转诊";
    public static final String MSG_CAT_1 = "CAT轻度预警,建议提醒注意";
    public static final String MSG_CAT_2 = "CAT中度预警,建议电话随访";
    public static final String MSG_CAT_3 = "CAT重度预警,请立即就诊！";
    public static final String MSG_PEF_1 = "PEF轻度预警，建议电话随访";
    public static final String MSG_PEF_2 = "PEF重度预警，请立即就诊！";
    public static final String MSG_DIS = "产生不适，建议随访了解情况";
    public static final String MSG_ANX_1 = "焦虑轻度预警,建议提醒注意";
    public static final String MSG_ANX_2 = "焦虑中度预警，建议电话随访";
    public static final String MSG_ANX_3 = "焦虑重度预警，请立即就诊！";
    public static final String MSG_DEP_1 = "抑郁轻度预警,建议提醒注意";
    public static final String MSG_DEP_2 = "抑郁中度预警，建议电话随访";
    public static final String MSG_DEP_3 = "抑郁重度预警，请立即就诊！";
    //ALERT REASON
    public static final String REASON_LAST = "最近一次数据预警";

}
