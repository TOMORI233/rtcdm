package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.zjubiomedit.service.PRServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringReader;

@RestController
@RequestMapping(value = "/pr", produces = "application/json;charset=UTF-8") //18908
public class PRController {

    @Autowired
    private PRServiceImpl prService;

    @RequestMapping("/fetchManage")
    public String fetchManage(@RequestBody String jsonString) {
        JsonObject jsonObject = transformJson(jsonString);
        String patientId = jsonObject.get("patientId").getAsString();
        return prService.getPRManagerInfo(patientId);
    }

    @RequestMapping("/updateManage")
    public String updateManage(@RequestBody String jsonString){
        JsonObject jsonObject = transformJson(jsonString);
        String patientId = jsonObject.get("patientId").getAsString();
        String weekPlan = jsonObject.get("weekPlan").getAsString();
        return prService.updatePRManagerInfo(patientId,weekPlan);
    }

    @RequestMapping("/fetchData")
    public String fetchData(@RequestBody String jsonString){
        JsonObject info = transformJson(jsonString);
        String patientId = info.get("patientId").getAsString();
        String startTime = info.get("startTime").getAsString();
        String endTime = info.get("endTime").getAsString();
        String type = info.get("type").getAsString();
        return prService.getDataRecordList(patientId, startTime, endTime, type);
    }

    @RequestMapping("/fetchLastData")
    public String fetchLastData(@RequestBody String jsonString){
        JsonObject info = transformJson(jsonString);
        String patientId = info.get("patientId").getAsString();
        int num = info.get("num").getAsInt();
        String type = info.get("type").getAsString();
        return prService.getLastDataRecordList(patientId, type, num);
    }

    @RequestMapping("/commitData")
    public String commitData(@RequestBody String jsonString){
        return prService.saveDataRecord(jsonString);
    }

    public JsonObject transformJson(String jsonString){
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(reader).getAsJsonObject();
        return object;
    }
}
