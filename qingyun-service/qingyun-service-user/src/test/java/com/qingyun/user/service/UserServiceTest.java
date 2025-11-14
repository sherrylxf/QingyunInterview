package com.qingyun.user.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingyun.user.entity.User;
import com.qingyun.user.mapper.UserMapper;
import com.qingyun.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * UserService单元测试
 *
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("用户服务测试")
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() throws Exception {
        // 初始化测试数据
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPhone("13800138000");
        testUser.setEmail("test@example.com");
        testUser.setPassword("$2a$10$encryptedPassword");
        testUser.setStatus(1);
        testUser.setCreateTime(LocalDateTime.now());
        
        // 使用反射设置父类的 baseMapper 字段
        // 因为 UserServiceImpl 继承了 ServiceImpl，baseMapper 是父类的字段
        Field baseMapperField = ServiceImpl.class.getDeclaredField("baseMapper");
        baseMapperField.setAccessible(true);
        baseMapperField.set(userService, userMapper);
    }

    @Test
    @DisplayName("根据手机号查询用户 - 成功")
    void testGetByPhone_Success() {
        // Given
        String phone = "13800138000";
        when(userMapper.selectByPhone(phone)).thenReturn(testUser);

        // When
        User result = userService.getByPhone(phone);

        // Then
        assertNotNull(result);
        assertEquals(phone, result.getPhone());
        assertEquals("testuser", result.getUsername());
        verify(userMapper, times(1)).selectByPhone(phone);
    }

    @Test
    @DisplayName("根据手机号查询用户 - 用户不存在")
    void testGetByPhone_NotFound() {
        // Given
        String phone = "13800138000";
        when(userMapper.selectByPhone(phone)).thenReturn(null);

        // When
        User result = userService.getByPhone(phone);

        // Then
        assertNull(result);
        verify(userMapper, times(1)).selectByPhone(phone);
    }

    @Test
    @DisplayName("根据邮箱查询用户 - 成功")
    void testGetByEmail_Success() {
        // Given
        String email = "test@example.com";
        when(userMapper.selectByEmail(email)).thenReturn(testUser);

        // When
        User result = userService.getByEmail(email);

        // Then
        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(userMapper, times(1)).selectByEmail(email);
    }

    @Test
    @DisplayName("根据邮箱查询用户 - 用户不存在")
    void testGetByEmail_NotFound() {
        // Given
        String email = "test@example.com";
        when(userMapper.selectByEmail(email)).thenReturn(null);

        // When
        User result = userService.getByEmail(email);

        // Then
        assertNull(result);
        verify(userMapper, times(1)).selectByEmail(email);
    }

    @Test
    @DisplayName("根据用户名查询用户 - 成功")
    void testGetByUsername_Success() {
        // Given
        String username = "testuser";
        when(userMapper.selectByUsername(username)).thenReturn(testUser);

        // When
        User result = userService.getByUsername(username);

        // Then
        assertNotNull(result);
        assertEquals(username, result.getUsername());
        verify(userMapper, times(1)).selectByUsername(username);
    }

    @Test
    @DisplayName("根据ID查询用户 - 成功")
    void testGetById_Success() {
        // Given
        Long userId = 1L;
        when(userMapper.selectById(userId)).thenReturn(testUser);

        // When
        User result = userService.getById(userId);

        // Then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userMapper, times(1)).selectById(userId);
    }

    @Test
    @DisplayName("保存用户 - 成功")
    void testSave_Success() {
        // Given
        User newUser = new User();
        newUser.setPhone("13900139000");
        newUser.setEmail("new@example.com");
        newUser.setPassword("password123");
        when(userMapper.insert(any(User.class))).thenReturn(1);

        // When
        boolean result = userService.save(newUser);

        // Then
        assertTrue(result);
        verify(userMapper, times(1)).insert(newUser);
    }

    @Test
    @DisplayName("保存用户 - 失败")
    void testSave_Failed() {
        // Given
        User newUser = new User();
        when(userMapper.insert(any(User.class))).thenReturn(0);

        // When
        boolean result = userService.save(newUser);

        // Then
        assertFalse(result);
        verify(userMapper, times(1)).insert(newUser);
    }

    @Test
    @DisplayName("更新用户 - 成功")
    void testUpdateById_Success() {
        // Given
        testUser.setStatus(0);
        when(userMapper.updateById(any(User.class))).thenReturn(1);

        // When
        boolean result = userService.updateById(testUser);

        // Then
        assertTrue(result);
        verify(userMapper, times(1)).updateById(testUser);
    }

    @Test
    @DisplayName("更新用户 - 失败")
    void testUpdateById_Failed() {
        // Given
        when(userMapper.updateById(any(User.class))).thenReturn(0);

        // When
        boolean result = userService.updateById(testUser);

        // Then
        assertFalse(result);
        verify(userMapper, times(1)).updateById(testUser);
    }
}

