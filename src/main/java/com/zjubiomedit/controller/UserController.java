package com.zjubiomedit.controller;

import com.zjubiomedit.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result userTest(@RequestParam String jsonstring){
        return new Result();
    }
}
