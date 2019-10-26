package com.zjubiomedit.util.enums;

import lombok.Getter;
import lombok.Setter;

public enum ErrorEnum {
    /*
     * 错误信息
     * */
    E_400(400, "请求处理异常，请稍后再试"),
    E_404(404, "请求路径不存在"),
    E_500(500, "请求方式有误,请检查 GET/POST"),
    E_501(501, "请求参数错误"),
    E_502(502, "网关错误"),

    E_10001(10001, "账号不存在"),
    E_10002(10002, "密码不正确"),
    E_10003(10003, "账户已存在"),

    E_20001(20001, "登陆已过期,请重新登陆");

    @Getter
    @Setter
    private int errorCode;

    @Getter
    @Setter
    private String errorMsg;

    ErrorEnum(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}

