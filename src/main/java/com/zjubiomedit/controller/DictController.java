package com.zjubiomedit.controller;

import com.zjubiomedit.entity.Dict.DivisionDict;
import com.zjubiomedit.entity.Dict.OrgDict;
import com.zjubiomedit.service.impl.DictServiceImpl;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "字典管理")
@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    DictServiceImpl dictService;

    @ApiOperation(value = "【后台】添加地域行政规划", response = Result.class)
    @PostMapping(value = "/division/add")
    public Result divisionAdd(@RequestBody DivisionDict divisionDict){
        return dictService.createDivision(divisionDict);
    }

    @ApiOperation(value = "【患者】获取行政区划列表", response = Result.class)
    @GetMapping(value = "/division/list")
    public Result divisionList(){
        return dictService.getDivision();
    }

    @ApiOperation(value = "【后台】添加医疗机构", response = Result.class)
    @PostMapping(value = "/org/add")
    public Result organizationAdd(@RequestBody OrgDict orgDict){
        return dictService.createOrg(orgDict);
    }

    @ApiOperation(value = "【患者】获取医疗机构列表", response = Result.class)
    @GetMapping(value = "/org/list")
    public Result organizationList(@RequestParam(value = "divisionCode") String divisionCode){
        return dictService.getOrgByDivision(divisionCode);
    }

    @ApiOperation(value = "【医生/医院】获取下属医院列表", response = Result.class)
    @GetMapping(value = "/org/subhospital")
    public Result hospitalSelectList(@RequestParam(value = "orgCode") String orgCode){
        return dictService.getHospitalList(orgCode);
    }

    @ApiOperation(value = "获取机构代码对应机构名称", response = Result.class)
    @GetMapping(value = "/org/name")
    public Result orgNameByOrgCode(@RequestParam(value = "orgCode") String orgCode){
        return dictService.getOrgNameByOrgCode(orgCode);
    }

}
