package com.oyyo.eduservice.feign.fallback;

import com.oyyo.commonUtils.Resp;
import com.oyyo.eduservice.feign.ServiceVodClient;
import org.springframework.stereotype.Component;


@Component
public class VodFileDegradeFeignClient implements ServiceVodClient {
    @Override
    public Resp deleteAliyunVideoByVideoId(String id) {

        return Resp.error().message("别慌，先抽根烟，程序员小哥正在抢修中！~_~");
    }
}
