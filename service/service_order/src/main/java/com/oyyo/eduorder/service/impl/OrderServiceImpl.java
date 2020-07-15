package com.oyyo.eduorder.service.impl;

import com.oyyo.commonUtils.Resp;
import com.oyyo.eduorder.entity.Order;
import com.oyyo.eduorder.feign.EduserviceClient;
import com.oyyo.eduorder.feign.UcenterClient;
import com.oyyo.eduorder.mapper.OrderMapper;
import com.oyyo.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyyo.eduservice.vo.CourseWebVo;
import com.oyyo.ucenter.api.UcenterApi;
import com.oyyo.ucenter.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private EduserviceClient eduserviceClient;

    /**
     * 创建订单
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public String createOrder(String courseId, String userId) {
        //远程调用获取用户信息
        Resp userResp = ucenterClient.getUserInfo(userId);
        Member member = (Member)userResp.getData().get("member");
        //远程调用获取课程信息
        Resp eduResp = eduserviceClient.queryCourseInfoAndTeacher(courseId);
        CourseWebVo courseWebVo = (CourseWebVo)eduResp.getData().get("courseWebVo");


        return null;
    }
}
