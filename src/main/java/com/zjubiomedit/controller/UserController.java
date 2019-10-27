package com.zjubiomedit.controller;

import com.google.gson.JsonObject;
import com.zjubiomedit.service.impl.AuthServiceImpl;
import com.zjubiomedit.util.JsonUtils;
import com.zjubiomedit.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private AuthServiceImpl authService;

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result doctorUserLogin(@RequestBody String loginUser){
        JsonObject jsonObject = JsonUtils.transformJson(loginUser);
        return authService.authLogin(jsonObject);
    }
}
