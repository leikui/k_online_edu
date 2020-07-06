package com.oyyo.ucenter.vo;

import lombok.Data;

/**
 * @ClassName: RegisterVO
 * @Description: TODO
 * @Author: LiKui
 * @Date: 2020-7-6 17:32
 * @Version: 1.0
 */
@Data
public class RegisterVO {

    private String nickName;
    private String mobile;
    private String password;
    private String verifyCode;
}
