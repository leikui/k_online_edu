package com.oyyo.eduorder.feign;

import com.oyyo.ucenter.api.UcenterApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("service-ucenter")
public interface UcenterClient extends UcenterApi {
}
