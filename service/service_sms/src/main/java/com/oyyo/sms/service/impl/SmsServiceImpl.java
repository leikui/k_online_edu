package com.oyyo.sms.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
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
    private String regionId = "cn-hangzhou";
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
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", regionId);
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", SignName);

        String code = RandomUtil.getSixBitRandom();
        log.info("验证码为：[{}]",code);
        request.putQueryParameter("TemplateCode", templateCode);
        Map<String,String> codeMap = new HashMap<>();
        codeMap.put("code",code);
        request.putQueryParameter("TemplateParam", JSON.toJSONString(codeMap));
        CommonResponse commonResponse;
        boolean resStatus = false;
        try {
            commonResponse = client.getCommonResponse(request);
            resStatus = commonResponse.getHttpResponse().isSuccess();
            if (resStatus) {
                redisTemplate.opsForValue().set(VERIFYCODE_PREFIX + phone, code, 3, TimeUnit.MINUTES);
            }
            log.info("短信发送返回结果:{}",commonResponse.getData());
        } catch (Exception e) {
            log.info("短信发送失败");
            e.printStackTrace();
            return false;
        }

        return commonResponse.getHttpResponse().isSuccess();
        }

}
