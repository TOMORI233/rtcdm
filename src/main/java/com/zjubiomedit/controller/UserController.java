package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.zjubiomedit.dto.DoctorEndDto.DoctorCreatePatientDto;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.service.impl.UserServiceImpl;
import com.zjubiomedit.util.JsonUtils;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "【综合】用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @ApiOperation(value = "【后台】新建医生账号")
    @PostMapping(value = "/doctor/create")
    public Result doctorUserSignin(@RequestBody DoctorUserAuths doctorUserAuths){
        return userService.createDoctorUser(doctorUserAuths);
    }

    @ApiOperation(value = "【医生】新建患者账号")
    @PostMapping(value = "/patient/create")
    public Result patientUserSignin(@RequestBody DoctorCreatePatientDto doctorCreatePatientDto) {
        return userService.createPatientUser(doctorCreatePatientDto);
    }


    @ApiOperation(value = "获取患者基本信息", response = Result.class)
    @PostMapping(value = "/patient/detail")
    public Result patientUserDetail(@RequestBody String jsonString){
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return userService.getPatientBaseInfo(jsonObject);
    }

}
