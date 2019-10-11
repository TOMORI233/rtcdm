package com.zjubiomedit.controller;

import com.zjubiomedit.service.DataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author yiiyuanliu
 * @Date 2018/4/10
 */

@RestController
//@RequestMapping("/COPDService.svc") //18909
@RequestMapping(value = "/data", produces = "application/json;charset=UTF-8") //18908
public class DataController {

    private final DataServiceImpl service;

    @Autowired
    public DataController(DataServiceImpl service) {
        this.service = service;
    }


    @RequestMapping("/commit") //18908
//    @RequestMapping("/CommitGenericRecord") //18909
    @ResponseBody
    public String commitData(@RequestBody String jsonString) {
        return service.commitData(jsonString);
    }

    @RequestMapping("/fetch") //18908
//    @RequestMapping("/GetGenericRecords") //18909
    @ResponseBody
    public String getData(@RequestBody String jsonString) {
        return service.fetchData(jsonString);
    }

    @RequestMapping("/GetLastGenericRecords")
    @ResponseBody
    public String getLastData(@RequestBody String jsonString) {
        return service.latestData(jsonString);
    }

    @RequestMapping("/MessageCommit")
    @ResponseBody
    public String commitMessage(@RequestBody String jsonString) {
        return service.commitMessage(jsonString);
    }

    @RequestMapping("/MessageFetch")
    @ResponseBody
    public String getLastMessage(@RequestBody String jsonString) {
        return service.latestMessage(jsonString);
    }

    @RequestMapping("/MessageUpdate")
    @ResponseBody
    public String updateMessage(@RequestBody String jsonString) {
        return service.updateMessage(jsonString);
    }
}
