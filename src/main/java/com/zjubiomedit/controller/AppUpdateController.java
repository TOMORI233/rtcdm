package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.zjubiomedit.service.AppService;
import com.zjubiomedit.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.StringReader;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/13
 */
@RestController
//@RequestMapping("/COPDService.svc") // 18909
public class AppUpdateController {

    @Autowired
    AppService appService;

    @RequestMapping(value = "/CheckAppUpdate", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String checkUpdate(@RequestBody String jsonString){
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(reader).getAsJsonObject();
        if (object.has("patientId") && object.has("versionCode")) {
            String patientId = object.get("patientId").getAsString();
            int version = object.get("versionCode").getAsInt();
            return appService.checkUpdate(patientId, version);
        } else {
            return Utils.errorFlag();
        }

    }
}
