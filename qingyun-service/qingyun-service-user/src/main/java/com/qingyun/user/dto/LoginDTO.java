package com.qingyun.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 用户登录DTO（统一登录DTO，通过字段判断登录类型）
 *
 */
@Data
public class LoginDTO {

    /**
     * 手机号（手机号登录时必填）
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 邮箱（邮箱登录时必填）
     */
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "邮箱格式不正确")
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 获取登录类型
     *
     * @return phone 或 email
     */
    public String getLoginType() {
        if (phone != null && !phone.isEmpty()) {
            return "phone";
        } else if (email != null && !email.isEmpty()) {
            return "email";
        }
        return null;
    }

    /**
     * 获取登录账号
     *
     * @return 手机号或邮箱
     */
    public String getLoginAccount() {
        if (phone != null && !phone.isEmpty()) {
            return phone;
        } else if (email != null && !email.isEmpty()) {
            return email;
        }
        return null;
    }
}

