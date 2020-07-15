package com.oyyo.eduorder.feign;

import com.oyyo.eduservice.api.EduApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("service-edu")
public interface EduserviceClient extends EduApi {
}
