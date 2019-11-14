package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.zjubiomedit.dto.PatientEndDto.RecordCommit;
import com.zjubiomedit.service.impl.RecordServiceImpl;
import com.zjubiomedit.util.JsonUtils;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */

@Api(tags = "数据管理")
@RestController
@RequestMapping("/data")
public class RecordController {

    @Autowired
    RecordServiceImpl recordService;
    /**
     *  患者自我管理
     */
    @ApiOperation(value = "【综合】新增数据记录", response = Result.class)
    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public Result dataCommit(@RequestBody RecordCommit recordCommit){
        return recordService.createDataRecord(recordCommit);
    }

    @ApiOperation(value = "【综合】获取时间区间数据记录", response = Result.class)
    @RequestMapping(value = "/fetch/list", method = RequestMethod.POST)
    public Result dataFetchList(@RequestBody String jsonString){
        //type
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return null;
    }

    @ApiOperation(value = "【综合】获取时间区间数据记录（分页）", response = Result.class)
    @RequestMapping(value = "/fetch/page", method = RequestMethod.POST)
    public Result dataFetchPage(@RequestBody String jsonString){
        //type
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return null;
    }

    @ApiOperation(value = "【综合】获取最近N条数据记录", response = Result.class)
    @RequestMapping(value = "/fetchLatest", method = RequestMethod.POST)
    public Result dataFetchLatest(@RequestBody String jsonString){
        //type
        JsonObject jsonObject = JsonUtils.transformJson(jsonString);
        return null;
    }

    @Autowired
    private KieContainer kieContainer;
    @GetMapping("/test")
    public void getAreaByPinCode() {
        KieSession kieSession = kieContainer.newKieSession();
        kieSession.insert(0d); // which object to validate
        kieSession.fireAllRules(); // fire all rules defined into drool file (drl)
        kieSession.dispose();
    }
}
