package com.zjubiomedit.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sun.xml.internal.bind.v2.TODO;
import com.zjubiomedit.dao.User.DoctorUserAuthsRepository;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.service.AuthService;
import com.zjubiomedit.util.Result;
import com.zjubiomedit.util.Utils;
import com.zjubiomedit.util.enums.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    DoctorUserAuthsRepository doctorUserAuthsRepository;

    @Override
    public Result authLogin(JsonObject jsonObject) {
//        Subject currentUser = SecurityUtils.getSubject();
//        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//        try {
//            currentUser.login(token);
//            info.put("result", "success");
//        } catch (AuthenticationException e) {
//            info.put("result", "fail");
//        }
        // 未使用shiro的登陆方式，暂用，其他调试成功添加shiro
        // TODO SHIRO 认证，授权
        System.out.println(jsonObject);

        if (jsonObject.has("account") && jsonObject.has("password")) {
            String userName = jsonObject.get("account").getAsString();
            String password = jsonObject.get("password").getAsString();
            Optional<DoctorUserAuths> userAuths = doctorUserAuthsRepository.findByUserName(userName);
            if (userAuths.isPresent()) {
                DoctorUserAuths user = userAuths.get();
                if (user.getStatus() == 0) {
                    if (user.getPassword().equals(password)) {
                        return new Result(user);
                    }
                    else {
                        return new Result(ErrorEnum.E_10002);
                    }
                } else {
                    return new Result(ErrorEnum.E_10004);
                }
            } else {
                return new Result(ErrorEnum.E_10001);
            }
        } else {
            return new Result(ErrorEnum.E_501);
        }
    }


}
