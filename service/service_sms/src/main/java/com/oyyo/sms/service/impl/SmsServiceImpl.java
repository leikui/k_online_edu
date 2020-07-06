package com.oyyo.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.oyyo.sms.service.SmsService;
import com.oyyo.sms.utils.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SmsServiceImpl
 * @Description: TODO
 * @Author: LiKui
 * @Date: 2020-7-6 11:39
 * @Version: 1.0
 */
@Service
@Slf4j
public class SmsServiceImpl implements SmsService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${sms.accessKeyId}")
    private String accessKeyId;
    @Value("${sms.accessKeySecret}")
    private String accessKeySecret;
    @Value("${sms.SignName}")
    private String SignName;
    @Value("${sms.templateCode}")
    private String templateCode;

    private static final String regionId = "cn-hangzhou";
    private static final String product = "Dysmsapi";
    private static final String domain = "dysmsapi.aliyuncs.com";
    private static final String VERIFYCODE_PREFIX = "PHONE_VERIFYCODE_";
    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    @Override
    public boolean sendRegisterSms(String phone) {

        String verifyCode = redisTemplate.opsForValue().get(VERIFYCODE_PREFIX + phone);
        if (!StringUtils.isEmpty(verifyCode)) {
            log.info("三分钟之内已经发送过短信，手机号码为：[{}],验证码为：[{}]",phone,JSON.parse(verifyCode));
            return false;
        }

        if (StringUtils.isEmpty(phone)) {
            log.info("手机号码为空");
            return false;
        }
        return sendSms(phone);

    }

    /**
     * 发送短信方法
      * @param phone
     * @return
     */
    private boolean sendSms(String phone) {

        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint(regionId,product,domain);

        IAcsClient client = new DefaultAcsClient(profile);
        SendSmsRequest request = new SendSmsRequest();
        request.setSysMethod(MethodType.POST);
        request.setTemplateCode(templateCode);
        request.setSignName(SignName);
        String code = RandomUtil.getSixBitRandom();
        log.info("验证码为：[{}]",code);
        Map<String,String> codeMap = new HashMap<>();
        codeMap.put("code",code);
        request.setTemplateParam(JSON.toJSONString(codeMap));
        request.setPhoneNumbers(phone);
        try {
            SendSmsResponse acsResponse = client.getAcsResponse(request);
            if (acsResponse.getCode() != null && StringUtils.equals(acsResponse.getCode(),"OK")) {
                redisTemplate.opsForValue().set(VERIFYCODE_PREFIX + phone, code, 3, TimeUnit.MINUTES);
                log.info("验证码发送成功");
                return true;
            }
            log.info("验证码发送失败");
            return false;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

}
