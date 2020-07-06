package com.oyyo.ucenter.service;

import com.oyyo.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import com.oyyo.ucenter.vo.RegisterVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author oy
 * @since 2020-07-06
 */
public interface MemberService extends IService<Member> {

    /**
     * 用户登录
     * @param member
     * @return
     */
    String login(Member member);

    /**
     * 用户注册
     * @param registerVO
     * @return
     */
    boolean register(RegisterVO registerVO);

    /**
     * 查询用户信息
     * @param request
     * @return
     */
    Member queryUserInfo(HttpServletRequest request);
}
