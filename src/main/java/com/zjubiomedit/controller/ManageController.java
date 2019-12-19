package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.zjubiomedit.dto.PagingDto.PagingDto;
import com.zjubiomedit.service.impl.ManageServiceImpl;
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
@Api(tags = "【医生】患者管理")
@RestController
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private ManageServiceImpl manageService;

    /**
     * 审核
     */
    @ApiOperation(value = "【医生】获取待审核患者信息（分页）", response = Result.class)
    @PostMapping(value = "/register/page")
    public Result patientRegisterPage(@RequestBody PagingDto pagingDto){ //POST应改为GET
        return new Result(manageService.pagingPatientRegister(pagingDto));
    }

    @ApiOperation(value = "【医生】审核（通过/拒绝）患者", response = Result.class)
    @PutMapping(value = "/register/audit")
    public Result patientRegisterAudit(@RequestParam Long serialNo){
        return manageService.reviewRegister(serialNo);
    }
    /**
     *  管理
     */
    @ApiOperation(value = "【医生】获取患者管理索引信息（分页）", response = Result.class)
    @RequestMapping(value = "/index/page", method = RequestMethod.POST)
    public Result patientManageIndexPage(@RequestBody String jsonString){
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return null;
    }

    /**
     * 随访
     */

    /**
     *  预警
     */
    @ApiOperation(value = "【医生】获取患者预警记录（分页）", response = Result.class)
    @RequestMapping(value = "/alert/page", method = RequestMethod.POST)
    public Result patientAlertPage(@RequestBody String jsonString){
        //单个患者预警记录表
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return null;
    }
    @ApiOperation(value = "【医生】获取预警记录", response = Result.class)
    @RequestMapping(value = "/alert/list", method = RequestMethod.POST)
    public Result patientAlertList(@RequestBody String jsonString){
        //患者预警总列表，根据患者id分组并分类
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return null;
    }
    @ApiOperation(value = "【医生】获取预警患者数量", response = Result.class)
    @RequestMapping(value = "/alert/count", method = RequestMethod.POST)
    public Result patientAlertCount(@RequestBody String jsonString){
        //预警患者的总数量
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return null;
    }
    @ApiOperation(value = "【医生】忽略患者预警", response = Result.class)
    @RequestMapping(value = "/alert/ignore", method = RequestMethod.POST)
    public Result patientAlertIgnore(@RequestBody String jsonString){
        //预警患者的总数量
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return null;
    }
}
