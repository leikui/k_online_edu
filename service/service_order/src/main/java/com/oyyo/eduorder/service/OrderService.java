package com.oyyo.eduorder.service;

import com.oyyo.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author oy
 * @since 2020-07-14
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单
     * @param courseId
     * @param userId
     * @return
     */
    String createOrder(String courseId, String userId);

    /**
     * 订单支付
     * @param order
     * @return
     */
    Map orderPay(Order order);

    /**
     * 支付成功 添加支付记录
     * @param request
     * @param response
     * @return
     */
    String paySuccess(HttpServletRequest request, HttpServletResponse response);

}
