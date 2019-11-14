package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.zjubiomedit.entity.Dict.DivisionDict;
import com.zjubiomedit.entity.Dict.OrgDict;
import com.zjubiomedit.service.impl.DictServiceImpl;
import com.zjubiomedit.util.JsonUtils;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Api(tags = "字典管理")
@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    DictServiceImpl dictService;

    @ApiOperation(value = "【后台】添加地域行政规划", response = Result.class)
    @RequestMapping(value = "/division/add", method = RequestMethod.POST)
    public Result divisionAdd(@RequestBody DivisionDict divisionDict){
        return dictService.createDivision(divisionDict);
    }

    @ApiOperation(value = "【患者】获取行政区划列表", response = Result.class)
    @RequestMapping(value = "/division/list")
    public Result divisionList(){
        return dictService.getDivision();
    }

    @ApiOperation(value = "【后台】添加医疗机构", response = Result.class)
    @RequestMapping(value = "/org/add", method = RequestMethod.POST)
    public Result organizationAdd(@RequestBody OrgDict orgDict){
        return dictService.createOrg(orgDict);
    }

    @ApiOperation(value = "【患者】获取医疗机构列表", response = Result.class)
    @RequestMapping(value = "/org/list", method = RequestMethod.POST)
    public Result organizationList(@RequestBody String divisionCode){
        JsonObject jsonObject = JsonUtils.transformJson(divisionCode);
        return dictService.getOrgByDivision(jsonObject);
    }
}
