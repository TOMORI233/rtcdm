package com.zjubiomedit.service;

import com.zjubiomedit.util.Result;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */
public interface AuthService {
    /**
     * 登录表单提交
     */
    Result authLogin(String userName, String password);
}
