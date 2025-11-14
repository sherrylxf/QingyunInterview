package com.qingyun.user.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 登录响应VO
 *
 */
@Data
public class LoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * Token过期时间（时间戳）
     */
    private Long expireTime;

    /**
     * 用户信息
     */
    private UserVO userInfo;
}

