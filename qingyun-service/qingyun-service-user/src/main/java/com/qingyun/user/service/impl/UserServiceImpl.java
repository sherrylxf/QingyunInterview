package com.qingyun.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingyun.user.entity.User;
import com.qingyun.user.mapper.UserMapper;
import com.qingyun.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 *
 * @author qingyun
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getByPhone(String phone) {return baseMapper.selectByPhone(phone);}

    @Override
    public User getByEmail(String email) {
        return baseMapper.selectByEmail(email);
    }

    @Override
    public User getByUsername(String username) {
        return baseMapper.selectByUsername(username);
    }

    @Override
    public User getById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public boolean save(User user) {
        return baseMapper.insert(user) > 0;
    }

    @Override
    public boolean updateById(User user) {
        return baseMapper.updateById(user) > 0;
    }
}

