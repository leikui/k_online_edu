package com.oyyo.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件
 * 继承 InitializingBean 接口 初始化配置
 */
@Component
public class ConstantVodPropertiesUtil implements InitializingBean {

    @Value("${vod.accessId}")
    private String  accessId;
    @Value("${vod.accessKey}")
    private String accessKey;

    //定义常量
    public static String ACCESS_ID;
    public static String ACCESS_KEY;

    /**
     *赋值常量
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_ID = accessId;
        ACCESS_KEY = accessKey;
    }
}
