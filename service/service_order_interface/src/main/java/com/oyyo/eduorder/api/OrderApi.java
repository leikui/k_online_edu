package com.oyyo.eduorder.api;

import com.oyyo.commonUtils.Resp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface OrderApi {
    @GetMapping("eduorder/order/courseIsBuy/{courseId}/{memberId}")
    Resp courseIsBuy(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
