package com.oyyo.eduorder.controller;


import com.oyyo.commonUtils.JwtUtils;
import com.oyyo.commonUtils.Resp;
import com.oyyo.eduorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author oy
 * @since 2020-07-14
 */
@RestController
@RequestMapping("/eduorder/order")
public class OrderController {

    @Autowired
    private OrderService orderService;


    /**
     * 创建订单
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("createOrder/{courseId}")
    public Resp saveOrder(@PathVariable("courseId")String courseId, HttpServletRequest request){

        String userId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId,userId);

        return Resp.ok().data("orderNo",orderNo);
    }
}

