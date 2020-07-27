package com.oyyo.eduorder.controller;


import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyyo.commonUtils.JwtUtils;
import com.oyyo.commonUtils.Resp;
import com.oyyo.eduorder.entity.Order;
import com.oyyo.eduorder.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author oy
 * @since 2020-07-14
 */
@RestController
@Slf4j
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

    /**
     * 查询订单信息
     * @param orderId
     * @return
     */
    @GetMapping("getOrderInfo/{orderId}")
    public Resp getOrderInfo(@PathVariable("orderId")String orderId){
        Order order = orderService.getOne(new QueryWrapper<Order>().eq("order_no", orderId));
        return Resp.ok().data("order", order);
    }

    /**
     * 订单支付
     * @return
     */
    @PostMapping("pay")
    public Resp orderPay(@RequestBody Order Order){
        Map map = orderService.orderPay(Order);
        return Resp.ok().data(map);
    }

    /**
     * 支付成功回调，真正减库存，增加用户积分，修改订单状态
     * 重定向到 课程详情页面
     * @return
     */
    @RequestMapping("pay/success")
    public void paySuccess(HttpServletRequest request, HttpServletResponse response) {
        String courseId = orderService.paySuccess(request,response);
        try {
            response.sendRedirect("http://localhost:3000/course/" + courseId);
            log.info("重定向到课程详情页面,课程id为:[{}]",courseId);
        } catch (IOException e) {
            log.error("重定向课程详情页失败，课程id为:[{}]",courseId);
        }
//        return "redirect:http://localhost:3000/course/" + courseId;
    }

    @GetMapping("/courseIsBuy/{courseId}/{memberId}")
    public Resp courseIsBuy(@PathVariable("courseId")String courseId,@PathVariable("memberId")String memberId){

        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);

        int count = orderService.count(wrapper);

        return Resp.ok().data("isBuy", count > 0);
    }
}

