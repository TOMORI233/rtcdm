package com.zjubiomedit.service;

import com.google.gson.JsonObject;
import com.zjubiomedit.util.Result;

public interface AuthService {
    /**
     * 登录表单提交
     */
    Result authLogin(JsonObject jsonObject);

    /**
     * 根据用户名和密码查询对应的用户
     *
     * @param username 用户名
     * @param password 密码
     */


    /**
     * 查询当前登录用户的权限等信息
     */


    /**
     * 退出登录
     */

}
