package com.oyyo.sms.controller;

import com.oyyo.commonUtils.Resp;
import com.oyyo.sms.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: SmsController
 * @Description: TODO
 * @Author: LiKui
 * @Date: 2020-7-6 11:38
 * @Version: 1.0
 */
@RestController
@RequestMapping("edusms/sms")
public class SmsController {

    @Autowired
    private SmsService smsService;

    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @PostMapping("sendSms/{phone}")
    public Resp sendRegisterSms(@PathVariable("phone")String phone){
        boolean isSend = smsService.sendRegisterSms(phone);
        return isSend ? Resp.ok() : Resp.ok().message("短信发送失败");
    }

}
