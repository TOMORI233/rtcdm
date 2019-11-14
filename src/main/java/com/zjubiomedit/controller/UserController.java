package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.zjubiomedit.entity.User.DoctorUserAuths;
import com.zjubiomedit.service.impl.UserServiceImpl;
import com.zjubiomedit.util.JsonUtils;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = "【综合】用户管理")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @ApiOperation(value = "【后台】新建医生账号")
    @ResponseBody
    @RequestMapping(value = "/doctor/create", method = RequestMethod.POST)
    public Result doctorUserSignin(@RequestBody DoctorUserAuths doctorUserAuths){
        return userService.createDoctorUser(doctorUserAuths);
    }

    @ApiOperation(value = "【医生】新建患者账号")
    @ResponseBody
    @RequestMapping(value = "/patient/create", method = RequestMethod.POST)
    public Result patientUserSignin(@RequestBody String signinUser){
        JsonObject jsonObject = JsonUtils.transformJson(signinUser);
        return userService.createPatientUser(jsonObject);
    }


    @ApiOperation(value = "获取患者基本信息", response = Result.class)
    @ResponseBody
    @RequestMapping(value = "/patient/detail", method = RequestMethod.POST)
    public Result patientUserDetail(@RequestBody String jsonString){
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return null;
    }

}
