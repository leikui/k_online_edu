package com.oyyo.eduservice.feign;

import com.oyyo.eduorder.api.OrderApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("edu-order")
public interface ServiceOrderClient extends OrderApi {
}
