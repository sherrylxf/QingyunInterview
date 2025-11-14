package com.qingyun.user.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UserVO单元测试
 *
 */
@DisplayName("用户信息VO测试")
class UserVOTest {

    @Test
    @DisplayName("UserVO基本功能测试")
    void testUserVO_Basic() {
        UserVO userVO = new UserVO();
        
        // 设置值
        userVO.setUserId(1L);
        userVO.setUsername("testuser");
        userVO.setPhone("138****8000");
        userVO.setEmail("t***@example.com");
        userVO.setAvatar("https://example.com/avatar.jpg");
        userVO.setStatus(1);
        userVO.setLastLoginTime(LocalDateTime.now());
        userVO.setCreateTime(LocalDateTime.now());
        
        // 验证值
        assertEquals(1L, userVO.getUserId());
        assertEquals("testuser", userVO.getUsername());
        assertEquals("138****8000", userVO.getPhone());
        assertEquals("t***@example.com", userVO.getEmail());
        assertEquals("https://example.com/avatar.jpg", userVO.getAvatar());
        assertEquals(1, userVO.getStatus());
        assertNotNull(userVO.getLastLoginTime());
        assertNotNull(userVO.getCreateTime());
    }

    @Test
    @DisplayName("UserVO序列化测试")
    void testUserVO_Serializable() {
        UserVO userVO = new UserVO();
        userVO.setUserId(1L);
        userVO.setUsername("testuser");
        
        // 验证实现了Serializable接口
        assertInstanceOf(java.io.Serializable.class, userVO);
    }
}

