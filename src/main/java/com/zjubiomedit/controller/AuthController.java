package com.zjubiomedit.controller;

import com.zjubiomedit.dto.DoctorEndDto.DoctorUserLogin;
import com.zjubiomedit.service.impl.AuthServiceImpl;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result doctorUserLogin(@RequestBody DoctorUserLogin doctorUserLogin){
//        JsonObject jsonObject = JsonUtils.transformJson(loginUser);
//        return authService.authLogin(jsonObject);
        return authService.authLogin(doctorUserLogin);
    }
}
