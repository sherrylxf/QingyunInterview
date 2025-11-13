package com.qingyun.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author qingyun
 */
@Slf4j
public class JwtUtils {

    /**
     * 默认密钥（实际项目中应从配置文件读取）
     */
    private static final String SECRET = "qingyun-platform-secret-key-2024-very-long-secret-key-for-jwt-token-generation";

    /**
     * 默认过期时间（7天）
     */
    private static final long EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    /**
     * 获取密钥
     */
    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成Token
     *
     * @param claims 载荷数据
     * @return Token字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return generateToken(claims, EXPIRE_TIME);
    }

    /**
     * 生成Token
     *
     * @param claims    载荷数据
     * @param expireTime 过期时间（毫秒）
     * @return Token字符串
     */
    public static String generateToken(Map<String, Object> claims, long expireTime) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(getSecretKey())
                .compact();
    }

    /**
     * 解析Token
     *
     * @param token Token字符串
     * @return Claims对象
     */
    public static Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            log.error("Token解析失败：{}", e.getMessage());
            return null;
        }
    }

    /**
     * 验证Token是否有效
     *
     * @param token Token字符串
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        Claims claims = parseToken(token);
        return claims != null && !claims.getExpiration().before(new Date());
    }

    /**
     * 从Token中获取用户ID
     *
     * @param token Token字符串
     * @return 用户ID
     */
    public static Long getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            Object userId = claims.get("userId");
            if (userId != null) {
                return Long.valueOf(userId.toString());
            }
        }
        return null;
    }
}

