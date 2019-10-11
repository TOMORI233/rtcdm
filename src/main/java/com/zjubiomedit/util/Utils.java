package com.zjubiomedit.util;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
public class Utils {
    public static final int SUCCESS = 200;
    public static final int FAILURE = 254;
    public static final int UNKNOWN = 255;
    public static final int DISABLED = 240;

    public static final String HEIGHT = "身高";
    public static final String WEIGHT = "体重";
    public static final String CM = "cm";
    public static final String KG = "kg";

    /**
     * 返回异常Flag
     * @return json字符串
     */
    public static String errorFlag() {
        Map<String, Object> map = new HashMap<>();
        Gson gson = new Gson();
        map.put("flag", Utils.UNKNOWN);
        return gson.toJson(map);
    }

    public static String formatDateToDay(Date date){
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        return ft.format(date);
    }
}
