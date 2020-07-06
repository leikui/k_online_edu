package com.oyyo.ucenter.controller;


import com.oyyo.commonUtils.JwtUtils;
import com.oyyo.commonUtils.Resp;
import com.oyyo.ucenter.entity.Member;
import com.oyyo.ucenter.service.MemberService;
import com.oyyo.ucenter.vo.RegisterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author oy
 * @since 2020-07-06
 */
@RestController
@RequestMapping("edu/ucenter/member")
public class MemberController {


    @Autowired
    private MemberService memberService;

    /**
     * 用户登录
     * @param member
     * @return
     */
    @PostMapping("login")
    public Resp login(@RequestBody Member member){
        String token = memberService.login(member);
        return Resp.ok().data("token",token);
    }

    /**
     * 用户注册
     * @param registerVO
     * @return
     */
    @PostMapping("register")
    public Resp register(@RequestBody RegisterVO registerVO){
        boolean regFlag = memberService.register(registerVO);

        return regFlag ? Resp.ok() : Resp.error().message("注册失败");
    }

    /**
     * 查询用户信息
     * @param request
     * @return
     */
    @GetMapping("getUserInfo")
    public Resp queryUserInfo(HttpServletRequest request){

        Member member = memberService.queryUserInfo(request);
        return Resp.ok().data("userInfo",member);
    }

}

