package com.zjubiomedit.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
public class Utils {

    //MANAGE INDEX LIST TYPE
    public static final int TYPE_ALL = 0;
    public static final int TYPE_MANAGING = 1;
    public static final int TYPE_REFERRAL_OUT = 2;
    public static final int TYPE_REFERRAL_IN = 3;
    //DICT ISVALID
    public static final int VALID = 1;
    public static final int INVALID = 0;
    //USER STATUS
    public static final int USER_ACTIVE = 0;
    public static final int USER_FREEZE = 1;
    public static final int USER_UNREVIEWED = 10;
    //USER AUTH
    public static final int PERSONAL = 0;
    public static final int GROUP = 1;
    //USER SEX  0-未知, 1-男, 2-女，9-...
    public static final int UNKNOWN = 0;
    public static final int MAN = 1;
    public static final int WOMAN = 2;
    //DATA RECORD TYPE
    public static final int CAT = 1;
    public static final int HAD = 2;
    public static final int PEF = 3;
    public static final int DRUG = 4;
    public static final int DISCOMFORT = 5;
    public static final int EVALUATION = 6;
    public static final int WEIGHT = 7;
    public static final int SMWT = 8;
    //message
    public static final int UNREAD = 0;
    public static final int READ = 1;
    public static final int FROMPATIENT = 0;
    public static final int FROMDOCTOR = 1;
    //ALERT STATUS
    public static final int ALERT_UNPROCESSED = 0;
    public static final int ALERT_FOLLOWEDUP = 1;
    public static final int ALERT_IGNORED = 2;
    //FOLLOWUP PLAN STATUS
    public static final int FOLLOW_PLAN_NOT_STARTED = 0;
    public static final int FOLLOW_PLAN_PROCESSING = 1;
    public static final int FOLLOW_PLAN_ABOLISHED = 2;
    //FOLLOWUP RECORD STATUS
    public static final int FOLLOW_RECORD_FAILED = 0;
    public static final int FOLLOW_RECORD_PROCESSING = 1;
    public static final int FOLLOW_RECORD_VALID = 2;
    //FOLLOWUP RECORD TEMPLATE CODE
    public static final int FOLLOW_TEMPLATE_COPD = 1;
    public static final int FOLLOW_TEMPLATE_ASTHMA = 2;
    //COPD MANAGE LEVEL
    public static final int LEVEL_NEW = 0;
    public static final int LEVEL_FIRST = 1;
    public static final int LEVEL_SECOND = 2;
    public static final int LEVEL_TERMINATED = 3;
    //MANAGE INDEX STATUS
    public static final int MANAGE_PROCESSING = 0;
    public static final int MANAGE_REFERRAL_UP = 1;
    public static final int MANAGE_REFERRAL_DOWN = 2;
    public static final int MANAGE_TERMINATED = 3;
    //MANAGE INDEX ACTIVE DEGREE
    public static final int MANAGE_INACTIVE = 0;
    public static final int MANAGE_ACTIVE= 1;
    //MANAGE APPLICATION REVIEW STATUS
    public static final int REVIEW_UNREVIEWED = 0;
    public static final int REVIEW_APPROVED = 1;
    public static final int REVIEW_FAILED = 2;
    public static final int REVIEW_IGNORED = 3;
    //MANAGE PLAN STATUS
    public static final int MANAGE_PLAN_NOT_STARTED = 0;
    public static final int MANAGE_PLAN_PROCESSING = 1;
    public static final int MANAGE_PLAN_ABOLISHED = 2;
    //REFERRAL STATUS
    public static final int REFERRAL_UNREVIEWED = 0;
    public static final int REFERRAL_APPROVED = 1;
    public static final int REFERRAL_FAILED = 2;
    public static final int REFERRAL_OVER = 3;
    //DISCOMFORT RECORD IS DISCOMFORT
    public static final int IS_COMFORT = 0;
    public static final int IS_DISCOMFORT = 1;

    public static String formatDateToDay(Date date){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        return ft.format(date);
    }
}
