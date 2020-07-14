package com.oyyo.eduorder.service.impl;

import com.oyyo.eduorder.entity.Order;
import com.oyyo.eduorder.mapper.OrderMapper;
import com.oyyo.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-07-14
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    /**
     * 创建订单
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public String createOrder(String courseId, String userId) {
        //远程调用获取用户信息

        //远程调用获取课程信息

        return null;
    }
}
