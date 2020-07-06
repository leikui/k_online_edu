package com.oyyo.sms.service;

/**
 * @author likui
 */
public interface SmsService {
    /**
     * 发送验证码
     * @param phone
     * @return
     */
    boolean sendRegisterSms(String phone);
}
