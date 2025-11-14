package com.qingyun.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 用户注册DTO
 *
 */
@Data
public class RegisterDTO {

    /**
     * 注册类型：phone/email
     */
    @NotBlank(message = "注册类型不能为空")
    @Pattern(regexp = "^(phone|email)$", message = "注册类型只能是phone或email")
    private String registerType;

    /**
     * 手机号（手机号注册时必填）
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 邮箱（邮箱注册时必填）
     */
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "邮箱格式不正确")
    private String email;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Pattern(regexp = "^.{6,}$", message = "密码长度至少6位")
    private String password;

    /**
     * 确认密码
     */
    @NotBlank(message = "确认密码不能为空")
    private String confirmPassword;

    /**
     * 验证码（手机/邮箱）
     */
    private String code;

}

