package com.oyyo.sms.api;

import com.oyyo.commonUtils.Resp;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 短信发送服务
 * @author likui
 */
public interface ServiceSmsapi {
    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @PostMapping("edusms/sms/sendSms/{phone}")
    Resp sendRegisterSms(@PathVariable("phone") String phone);
}
