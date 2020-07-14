package com.oyyo.eduorder.service.impl;

import com.oyyo.eduorder.entity.PayLog;
import com.oyyo.eduorder.mapper.PayLogMapper;
import com.oyyo.eduorder.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-07-14
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

}
