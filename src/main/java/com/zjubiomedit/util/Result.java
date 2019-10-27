package com.zjubiomedit.util;

import com.zjubiomedit.util.enums.ErrorEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;


public class Result<T> {

    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private Object data;

    public Result (ErrorEnum error) {
        this.code = error.getErrorCode();
        this.message = error.getErrorMsg();
    }
    public Result (int code, String message) {
        this.code = code;
        this.message = message;
    }
    public Result () {
        this.code = 0;
        this.message = "success";
    }

    public Result(Object data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

}
