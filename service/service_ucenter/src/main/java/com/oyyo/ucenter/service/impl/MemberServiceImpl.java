package com.oyyo.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oyyo.commonUtils.JwtUtils;
import com.oyyo.serviceBase.handler.BaseException;
import com.oyyo.ucenter.entity.Member;
import com.oyyo.ucenter.mapper.MemberMapper;
import com.oyyo.ucenter.service.MemberService;
import com.oyyo.ucenter.vo.RegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author oy
 * @since 2020-07-06
 */
@Service
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    //验证码前缀
    private static final String VERIFYCODE_PREFIX = "PHONE_VERIFYCODE_";

    /**
     * 用户登录
     *
     * @param member
     * @return
     */
    @Override
    public String login(Member member) {
        String mobile = member.getMobile();
        String password = member.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            log.info("非法参数");
            throw new BaseException(20003, "非法参数");
        }

        Member memberInfo = this.getOne(new QueryWrapper<Member>().eq("mobile", mobile));
        if (memberInfo == null) {
            log.info("用户不存在");
            throw new BaseException(20001, "用户不存在");
        }

        //盐值
        String salt = memberInfo.getSalt();
        //加密判断密码
        if (!DigestUtils.md5Hex(salt + password + salt).equals(memberInfo.getPassword())) {
            log.info("密码错误");
            throw new BaseException(20001, "密码错误");
        }
        if (memberInfo.getIsDisabled()) {
            log.info("用户禁止登录");
            throw new BaseException(20001, "用户禁止登录");
        }

        log.info("用户数据校验通过，开始生成 token");
        String token = JwtUtils.getJwtToken(memberInfo.getId(), memberInfo.getNickname());
        log.info("生成的 token 为：[{}]",token);
        return token;
    }

    /**
     * 用户注册
     * @param registerVO
     * @return
     */
    @Override
    public boolean register(RegisterVO registerVO) {

        String mobile = registerVO.getMobile();
        String nickName = registerVO.getNickName();
        String password = registerVO.getPassword();
        String verifyCode = registerVO.getVerifyCode();

        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickName)
                ||StringUtils.isEmpty(password) || StringUtils.isEmpty(verifyCode)) {
            throw new BaseException(20003, "参数非法");
        }

        log.info("用户输入的验证码为：[{}]",verifyCode);
        String verifyCodeServer = redisTemplate.opsForValue().get(VERIFYCODE_PREFIX + mobile);
        if (StringUtils.isEmpty(verifyCodeServer)) {
            log.info("验证码未发送或超过三分钟");
            return false;
        }

        boolean flag = StringUtils.equals(registerVO.getVerifyCode(), verifyCodeServer);
        if (!flag) {
            log.info("验证码输入错误");
            return false;
        }
        log.info("验证码通过，开始注册");

        int count = this.count(new QueryWrapper<Member>().eq("mobile", mobile));
        if (count > 0 ){
            log.info("手机号已存在，开始注册");
            return false;
        }
        Member member = new Member();
        member.setMobile(mobile);
        member.setNickname(nickName);
        //生成盐
        String salt = UUID.randomUUID().toString().substring(0, 6);
        member.setSalt(salt);
        String passwordWithMd5 = DigestUtils.md5Hex(salt + password + salt);
        member.setPassword(passwordWithMd5);
        member.setIsDisabled(false);

        return this.save(member);
    }

    /**
     * 查询用户信息
     * @param request
     * @return
     */
    @Override
    public Member queryUserInfo(HttpServletRequest request) {
        String token = JwtUtils.getMemberIdByJwtToken(request);
        Member member = this.getById(token);
        //过滤用户信息
        member.setPassword(null);
        member.setSalt(null);
        member.setIsDeleted(null);
        return member;
    }
}
