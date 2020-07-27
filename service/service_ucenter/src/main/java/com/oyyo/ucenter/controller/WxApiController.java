package com.oyyo.ucenter.controller;

import com.oyyo.ucenter.service.WxApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/api/ucenter/wx")
public class WxApiController {

    @Autowired
    private WxApiService wxApiService;


    /**
     * 获取微信登录 二维码
     * @return
     */
    @GetMapping("login")
    public String genQrConnect(){
        String url = wxApiService.getTwoCode();

        return "redirect:" + url;
    }
}
