package com.qingyun.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 认证过滤器
 * 统一处理Token验证
 *
 * @author qingyun
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {

    /**
     * 白名单路径（不需要认证的路径）
     */
    private static final String[] WHITE_LIST = {
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/captcha"
    };

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        // 白名单路径直接放行
        if (isWhiteList(path)) {
            return chain.filter(exchange);
        }

        // 获取Token
        String token = getToken(request);
        if (!StringUtils.hasText(token)) {
            return unauthorized(exchange.getResponse(), "未携带Token");
        }

        // TODO: 验证Token有效性（可以调用auth服务验证）
        // 这里暂时简单处理，实际应该调用auth服务验证Token

        // 将Token传递给下游服务
        ServerHttpRequest newRequest = request.mutate()
                .header("Authorization", "Bearer " + token)
                .build();

        return chain.filter(exchange.mutate().request(newRequest).build());
    }

    /**
     * 判断是否为白名单路径
     */
    private boolean isWhiteList(String path) {
        for (String whitePath : WHITE_LIST) {
            if (path.startsWith(whitePath)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取Token
     */
    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return request.getQueryParams().getFirst("token");
    }

    /**
     * 返回未授权响应
     */
    private Mono<Void> unauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(
                Mono.just(response.bufferFactory().wrap(message.getBytes()))
        );
    }

    @Override
    public int getOrder() {
        return -100;
    }
}

