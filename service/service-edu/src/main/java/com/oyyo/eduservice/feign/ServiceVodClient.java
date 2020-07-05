package com.oyyo.eduservice.feign;

import com.oyyo.eduservice.feign.fallback.VodFileDegradeFeignClient;
import com.oyyo.vod.api.EduVodApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "service-vod",fallback = VodFileDegradeFeignClient.class)
public interface ServiceVodClient extends EduVodApi {
}
