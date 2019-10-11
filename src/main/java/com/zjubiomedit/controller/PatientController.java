package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.zjubiomedit.service.PatientService;
import com.zjubiomedit.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.StringReader;

/**
 * @Author yiiyuanliu
 * @Date 2018/6/11
 */
@RestController
//@RequestMapping("/COPDService.svc") // 18909
public class PatientController {

    @Autowired
    PatientService patientService;

    @RequestMapping(value = "/WapLogin", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String login(@RequestBody String jsonString) {
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(reader).getAsJsonObject();
        if (object.has("account") && object.has("password")) {
            String patientId = object.get("account").getAsString();
            String password = object.get("password").getAsString();
            return patientService.doLogin(patientId, password);
        } else {
            return Utils.errorFlag();
        }
    }

    @RequestMapping(value = "/ValidateRegister", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String valid(@RequestBody String jsonString) {
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(reader).getAsJsonObject();
        if (object.has("patientId") && object.has("patientName")) {
            String patientId = object.get("patientId").getAsString();
            String patientName = object.get("patientName").getAsString();
            return patientService.validRegister(patientId, patientName);
        } else {
            return Utils.errorFlag();
        }

    }

    @RequestMapping(value = "/WapRegistWithPatientInfo", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String register(@RequestBody String jsonString) {
        JsonReader reader = new JsonReader(new StringReader(jsonString));
        JsonParser jsonParser = new JsonParser();
        JsonObject object = jsonParser.parse(reader).getAsJsonObject();
        if (object.has("patientId") && object.has("info")) {
            String patientId = object.get("patientId").getAsString();
            String info = object.get("info").getAsString();
            return patientService.doRegister(patientId, info);
        } else {
            return Utils.errorFlag();
        }
    }

    @RequestMapping(value = "/WapAllProvince", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getAllProvinces() {
        return patientService.getAllProvince();
    }

    @RequestMapping(value = "/WapGetHospital", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getHospitalByProvince(@RequestParam String province) {
        return patientService.getAllHospital(province);
    }

    @RequestMapping(value = "/WapGetDoctor", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Object getDoctorByHospital(@RequestParam String hospitalCode) {
        return patientService.getAllDoctor(hospitalCode);
    }

}
