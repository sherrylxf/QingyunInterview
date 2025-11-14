package com.qingyun.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingyun.user.entity.User;

/**
 * 用户服务接口
 *
 * @author qingyun
 */
public interface UserService extends IService<User> {

    /**
     * 根据手机号查询用户
     *
     * @param phone 手机号
     * @return 用户信息
     */
    User getByPhone(String phone);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户信息
     */
    User getByEmail(String email);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User getByUsername(String username);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User getById(Long id);

    /**
     * 保存用户
     *
     * @param user 用户信息
     * @return 是否成功
     */
    boolean save(User user);

    /**
     * 更新用户
     *
     * @param user 用户信息
     * @return 是否成功
     */
    boolean updateById(User user);
}

