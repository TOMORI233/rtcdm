package com.zjubiomedit.util;

import com.google.gson.Gson;
import com.zjubiomedit.entity.User.PatientUserBaseInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
public class Utils {

    //DICT ISVALID
    public static final int VALID = 1;
    public static final int INVALID = 0;
    //USER STATUS
    public static final int USER_ACTIVE = 0;
    public static final int USER_FREEZE = 1;
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


    public static String formatDateToDay(Date date){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        return ft.format(date);
    }
}
