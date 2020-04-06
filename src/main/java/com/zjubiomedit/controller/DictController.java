package com.zjubiomedit.controller;

import com.zjubiomedit.service.impl.DictServiceImpl;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "字典管理")
@RestController
@RequestMapping("/dict")
public class DictController {
    @Autowired
    DictServiceImpl dictService;

//    @ApiOperation(value = "【后台】添加地域行政规划", response = Result.class)
//    @RequestMapping(value = "/division/add", method = RequestMethod.POST)
//    public Result divisionAdd(@RequestBody DivisionDict divisionDict){
//        return dictService.createDivision(divisionDict);
//    }

//    @ApiOperation(value = "【患者】获取行政区划列表", response = Result.class)
//    @GetMapping(value = "/division/list")
//    public Result divisionList(){
//        return dictService.getDivision();
//    }

//    @ApiOperation(value = "【后台】添加医疗机构", response = Result.class)
//    @RequestMapping(value = "/org/add", method = RequestMethod.POST)
//    public Result organizationAdd(@RequestBody OrgDict orgDict){
//        return dictService.createOrg(orgDict);
//    }

//    @ApiOperation(value = "【患者】获取医疗机构列表", response = Result.class)
//    @RequestMapping(value = "/org/list", method = RequestMethod.POST)
//    public Result organizationList(@RequestBody String divisionCode){
//        JsonObject jsonObject = JsonUtils.transformJson(divisionCode);
//        return dictService.getOrgByDivision(jsonObject);
//    }

    @ApiOperation(value = "【医生】获取下属医院列表", response = Result.class)
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
