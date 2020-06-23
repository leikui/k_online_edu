package com.oyyo.eduService.controller;

import com.oyyo.commonUtils.Resp;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")

public class EduUserLoginController {

    @PostMapping("login")
    public Resp login(){
        return Resp.ok().data("token","admin");
    }

    @GetMapping("info")
    public Resp getInfo(){
        return Resp.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
