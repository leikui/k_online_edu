package com.oyyo.eduorder.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.oyyo.commonUtils.Resp;
import com.oyyo.eduorder.entity.Order;
import com.oyyo.eduorder.entity.PayLog;
import com.oyyo.eduorder.feign.EduserviceClient;
import com.oyyo.eduorder.feign.UcenterClient;
import com.oyyo.eduorder.mapper.OrderMapper;
import com.oyyo.eduorder.mapper.PayLogMapper;
import com.oyyo.eduorder.pay.AlipayTemplate;
import com.oyyo.eduorder.pay.PayVo;
import com.oyyo.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyyo.eduservice.vo.CourseWebVo;
import com.oyyo.ucenter.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-07-14
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private UcenterClient ucenterClient;
    @Autowired
    private EduserviceClient eduserviceClient;
    @Autowired
    private AlipayTemplate payTemplate;
    @Autowired
    private PayLogMapper payLogMapper;

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
        Object m = userResp.getData().get("member");
        //object 不能强转 实体 借助json墙砖
        Member member = JSON.parseObject(JSON.toJSONString(m), Member.class);
        //远程调用获取课程信息
        Resp eduResp = eduserviceClient.queryCourseInfoAndTeacher(courseId);
        Object cou = eduResp.getData().get("courseWebVo");
        CourseWebVo courseWebVo = JSON.parseObject(JSON.toJSONString(cou), CourseWebVo.class);

        Order order = new Order();
        order.setMemberId(userId);
        order.setOrderNo(UUID.randomUUID().toString().replaceAll("-",""));
        order.setCourseId(courseId);
        order.setCourseCover(courseWebVo.getCover());
        order.setCourseTitle(courseWebVo.getTitle());
        order.setNickname(member.getNickname());
        order.setPayType(0);
        order.setStatus(0);
        order.setTeacherName(courseWebVo.getTeacherName());
        order.setMobile(member.getMobile());
        order.setTotalFee(courseWebVo.getPrice());
        order.setIsDeleted(0);
        this.save(order);

        log.info("订单编号：[{}]",order.getOrderNo());
        return order.getOrderNo();
    }

    /**
     * 订单支付
     * @param order
     * @return
     */
    @Override
    public Map orderPay(Order order) {
        String formResult = null;
        log.info("支付的订单号为：[{}],金额为：[{}]",order.getOrderNo()
                ,order.getTotalFee());
        try {
            PayVo payVo = new PayVo();
            payVo.setOut_trade_no(order.getOrderNo());
            payVo.setTotal_amount(order.getTotalFee().toString());
            payVo.setBody(order.getOrderNo());
            payVo.setSubject( "订单号：" + order.getOrderNo());
            formResult = payTemplate.pay(payVo);
            log.info("返回form表单为：[{}]",formResult);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            log.info("调用支付宝接口异常");
        }
        Map map = new HashMap();
        map.put("courseId", order.getCourseId());
        map.put("orderNo", order.getOrderNo());
        map.put("payForm", formResult);
        return map;
    }

    /**
     * 修改订单状态， 添加支付记录
     *
     * 这里选择 同步回调， 异步回调需要 设置 公网域名
     * @param request
     * @param response
     * @return
     */
    @Override
    public String paySuccess(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("=================================同步回调=====================================");

        // 获取支付宝GET过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        //订单号
        String out_trade_no = null;
        try {
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("utf-8"), "utf-8");
                params.put(name, valueStr);
            }

            System.out.println(params);//查看参数都有哪些
            // 商户订单号
            out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");

            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");
            //String msg = new String(request.get("msg").getBytes("ISO-8859-1"), "UTF-8");

            System.out.println("商户订单号="+out_trade_no);
            System.out.println("支付宝交易号="+trade_no);
            System.out.println("付款金额="+total_amount);

            Order order = this.getOne(new QueryWrapper<Order>().eq("order_no", out_trade_no));
            if (order.getStatus().intValue() == 1) {
                return out_trade_no;
            }
            order.setStatus(1);
            //更新订单状态
            this.updateById(order);

            //添加支付记录

            PayLog payLog = new PayLog();
            payLog.setOrderNo(out_trade_no);
            payLog.setPayTime(new Date());
            payLog.setPayType(1);
            payLog.setTotalFee(new BigDecimal(total_amount));
            payLog.setTradeState("success");
            payLog.setTransactionId(trade_no);

            payLog.setAttr(JSON.toJSONString(params));
            payLogMapper.insert(payLog);

        }catch (Exception e){
            log.info("添加支付失败",e);
        }
        // 根据订单id查询订单信息
        Order order = this.getOne(new QueryWrapper<Order>().eq("order_no",out_trade_no));
        if (order != null) {
            //返回课程id
            return order.getCourseId();
        }
        return null;
    }

}
