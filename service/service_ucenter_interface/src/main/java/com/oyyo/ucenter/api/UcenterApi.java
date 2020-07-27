package com.oyyo.ucenter.api;

import com.oyyo.commonUtils.Resp;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 用户信息接口
 */
public interface UcenterApi {
    /**
     * 根据id获取用户信息
     * @param id
     * @return
     */
    @PostMapping("edu/ucenter/member/getUserInfo/{id}")
    Resp getUserInfo(@PathVariable("id")String id);
}
