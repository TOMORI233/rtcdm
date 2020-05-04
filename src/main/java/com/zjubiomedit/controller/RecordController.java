package com.zjubiomedit.controller;

import com.zjubiomedit.dto.PatientEndDto.RecordCommitDto;
import com.zjubiomedit.service.impl.RecordServiceImpl;
import com.zjubiomedit.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author leiyi sheng
 * @version 1.0
 * @date 2019-11-04
 */

@Api(tags = "数据管理")
@RestController
@RequestMapping("/data")
@CrossOrigin
public class RecordController {

    @Autowired
    RecordServiceImpl recordService;
    /**
     *  患者自我管理
     */
    @ApiOperation(value = "【综合】新增数据记录", response = Result.class)
    @PostMapping(value = "/commit")
    public Result dataCommit(@RequestBody RecordCommitDto recordCommitDto){
        return recordService.createDataRecord(recordCommitDto);
    }

    @ApiOperation(value = "【综合】获取时间区间数据记录", response = Result.class)
    @ApiImplicitParams(@ApiImplicitParam(name = "type", dataType = "int", value = "1-CAT 2-HAD 3-PEF 4-DRUG 5-DIS 6-EVA 7-WEI 8-SMW", required = true, defaultValue = "1", example = "1"))
    @GetMapping(value = "/fetch/list")
    public Result dataFetchList(Integer type,
                                @RequestParam(value = "patientID") Long patientID,
                                @RequestParam(value = "startDate") Date startDate,
                                @RequestParam(value = "endDate") Date endDate){
        return recordService.fetchRecordList(type, patientID, startDate, endDate);
    }

//    @ApiOperation(value = "【综合】获取时间区间数据记录（分页）", response = Result.class)
//    @GetMapping(value = "/fetch/page")
//    public Result dataFetchPage(@RequestParam(value = "type") Integer type,
//                                @RequestParam(value = "patientID") Long patientID,
//                                @RequestParam(value = "startDate") Date startDate,
//                                @RequestParam(value = "endDate") Date endDate,
//                                @RequestParam(value = "pageIndex") Integer pageIndex,
//                                @RequestParam(value = "pageOffset") Integer pageOffset){
//        return recordService.fetchRecordPage(type, patientID, startDate, endDate, pageIndex, pageOffset);
//    }

    @ApiOperation(value = "【综合】获取最近N条数据记录", response = Result.class)
    @ApiImplicitParams(@ApiImplicitParam(name = "type", dataType = "int", value = "1-CAT 2-HAD 3-PEF 4-DRUG 5-DIS 6-EVA 7-WEI 8-SMW", required = true, defaultValue = "1", example = "1"))
    @GetMapping(value = "/fetch/latest")
    public Result dataFetchLatest(Integer type,
                                  @RequestParam(value = "patientID") Long patientID,
                                  @RequestParam(value = "N") Integer N){
        return recordService.fetchLatestRecord(type, patientID, N);
    }

//    @Autowired
//    private KieContainer kieContainer;
//    @GetMapping("/test")
//    public void getAreaByPinCode() {
//        KieSession kieSession = kieContainer.newKieSession();
//        kieSession.insert(0d); // which object to validate
//        kieSession.fireAllRules(); // fire all rules defined into drool file (drl)
//        kieSession.dispose();
//    }
}
