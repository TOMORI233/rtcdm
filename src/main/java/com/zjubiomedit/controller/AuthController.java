package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.zjubiomedit.service.impl.AuthServiceImpl;
import com.zjubiomedit.util.JsonUtils;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */

@Api(tags = "用户权限")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    @ApiOperation(value = "医生用户登陆", httpMethod = "POST")
    @PostMapping(value = "/login")
    public Result doctorUserLogin(@RequestBody String loginUser){
        JsonObject jsonObject = JsonUtils.transformJson(loginUser);
        return authService.authLogin(jsonObject);
    }
}
