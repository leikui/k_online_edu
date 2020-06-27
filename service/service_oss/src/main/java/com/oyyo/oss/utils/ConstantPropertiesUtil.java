package com.oyyo.oss.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 读取配置文件
 * 继承 InitializingBean 接口 初始化配置
 */
@Component
public class ConstantPropertiesUtil implements InitializingBean {

    @Value("${oos.accessId}")
    private String  accessId;
    @Value("${oos.accessKey}")
    private String accessKey;
    @Value("${oos.endpoint}")
    private String endpoint;
    @Value("${oos.bucket}")
    private String bucket;

    //定义常量
    public static String ACCESS_ID;
    public static String ACCESS_KEY;
    public static String END_POINT;
    public static String BUCKET_NAME;

    /**
     *赋值常量
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_ID = accessId;
        ACCESS_KEY = accessKey;
        END_POINT = endpoint;
        BUCKET_NAME = bucket;
    }
}
