# 青云面试平台（Qingyun Platform）

## 📋 项目简介

青云面试平台是一个基于 Spring Cloud Alibaba 的微服务架构项目，提供打卡、题库、用户管理、统计报表、学习成长等核心功能。

## 🏗️ 项目架构

```
qingyun-platform/
├── qingyun-gateway/              # 网关模块（统一入口，端口：8080）
├── qingyun-auth/                 # 认证中心（JWT/OAuth2登录认证，端口：8000）
├── qingyun-common/               # 通用工具包（公共类库）
├── qingyun-service/              # 服务模块父工程
│   ├── qingyun-service-user/     # 用户服务（端口：8001）
│   ├── qingyun-service-question/ # 题库服务（端口：8002）
│   ├── qingyun-service-record/   # 打卡记录服务（端口：8003）
│   ├── qingyun-service-admin/    # 管理员服务（端口：8004）
│   └── qingyun-service-stat/     # 统计分析服务（端口：8005）
├── qingyun-api/                  # OpenAPI 接口定义（feign client + dto）
├── qingyun-web-admin/            # 管理端后端（端口：9001）
├── qingyun-web-user/             # 用户端后端（端口：9002）
└── docs/                         # 项目文档
```

## 🛠️ 技术栈

| 模块 | 技术栈 |
| ---- | ------ |
| 后端框架 | Spring Boot 3.3.2 + Spring Cloud 2023.0.1 |
| 数据访问 | MyBatis-Plus 3.5.7 + MySQL 8.0.33 |
| 注册中心 | Nacos |
| 配置中心 | Nacos Config |
| 网关 | Spring Cloud Gateway |
| 认证授权 | JWT + Spring Security |
| 缓存 | Redis |
| API文档 | Knife4j 4.4.0 |
| 工具类 | Hutool 5.8.25 |

## 📦 模块说明

### 1. qingyun-gateway（网关服务）
- 统一入口，路由转发请求到不同微服务
- 权限验证与限流
- 统一响应格式、跨域配置、接口防刷

### 2. qingyun-auth（认证中心）
- 用户登录注册（手机号、邮箱、微信登录）
- JWT Token 生成与验证
- 登录态管理、权限验证

### 3. qingyun-common（通用工具包）
- 全局异常处理、返回封装 (`R<T>`)
- 通用工具类（JwtUtils、DateUtils等）
- 实体基类（BaseEntity、PageResult）
- 自定义注解（`@LoginRequired`, `@AdminOnly`）

### 4. qingyun-service-user（用户服务）
- 用户注册 / 登录 / 信息维护
- 学习积分、等级成长体系
- 用户打卡数据汇总
- 用户学习曲线可视化

### 5. qingyun-service-question（题库服务）
- 面试题、题库管理
- 分类（算法、Java、数据库、系统设计等）
- 标签系统（难度、热度）
- 支持题目导入导出（Excel、JSON）
- 支持收藏、错题集功能

### 6. qingyun-service-record（打卡记录服务）
- 用户打卡（每日签到 / 练习打卡）
- 答题记录（题目ID、结果、用时）
- 连续打卡统计、习惯养成

### 7. qingyun-service-admin（管理员服务）
- 管理员用户、角色、权限
- 题库审核、内容发布
- 管理后台接口

### 8. qingyun-service-stat（统计分析服务）
- 打卡趋势分析（每日活跃、学习时长）
- 用户学习习惯可视化（ECharts）
- 后台仪表盘数据提供接口

### 9. qingyun-api（API接口定义）
- 提供服务间调用的统一接口定义（FeignClient）
- DTO / VO / Feign 接口统一管理

### 10. qingyun-web-user（用户端后端）
- 用户端 Web 接口网关层
- 提供 RESTful API 给前端（Vue / 小程序）
- 支持统一 Token 验证、参数校验、限流

### 11. qingyun-web-admin（管理端后端）
- 管理端后端接口
- 连接管理前端（Vue + Element Plus）
- 权限菜单、角色管理

## 🚀 快速开始

### 环境要求
- JDK 21+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- Nacos 2.0+

### 启动步骤

1. **启动 Nacos**
   ```bash
   # 下载并启动 Nacos
   startup.cmd -m standalone
   ```

2. **启动 MySQL 和 Redis**
   ```bash
   # 确保 MySQL 和 Redis 服务已启动
   ```

3. **创建数据库**
   ```sql
   CREATE DATABASE qingyun_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

4. **编译项目**
   ```bash
   mvn clean install -DskipTests
   ```

5. **启动服务（按顺序）**
   ```bash
   # 1. 启动认证服务
   cd qingyun-auth
   mvn spring-boot:run
   
   # 2. 启动各个业务服务
   cd qingyun-service/qingyun-service-user
   mvn spring-boot:run
   
   # 3. 启动网关服务
   cd qingyun-gateway
   mvn spring-boot:run
   ```

### 访问地址

- **网关入口**: http://localhost:8080
- **API文档（Knife4j）**: 
  - 认证服务: http://localhost:8000/doc.html
  - 用户服务: http://localhost:8001/doc.html
  - 题库服务: http://localhost:8002/doc.html
  - 打卡记录服务: http://localhost:8003/doc.html
  - 管理员服务: http://localhost:8004/doc.html
  - 统计分析服务: http://localhost:8005/doc.html

## 📝 配置说明

### Nacos 配置

所有服务的配置都从 Nacos 配置中心读取，需要在 Nacos 中创建以下配置：

- **命名空间**: `qingyun-dev`
- **公共配置**: `qingyun-common.yml`

### 数据库配置

在 `application.yml` 中配置数据库连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/qingyun_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: root
```

### Redis 配置

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password:
      database: 0
```

## 📚 开发规范

### 包结构规范

```
com.qingyun.{module}/
├── controller/       # 控制层
├── service/          # 业务层接口
├── service/impl/     # 业务层实现
├── mapper/           # MyBatis Mapper 接口
├── entity/           # 实体类（对应数据库表）
├── dto/              # 数据传输对象（请求参数）
├── vo/               # 视图对象（响应数据）
├── config/           # 配置类
└── utils/            # 工具类
```

### 命名规范

- **服务名**: `qingyun-service-xxx`
- **包路径**: `com.qingyun.xxx`
- **数据库表前缀**: `qy_`（如：`qy_user`, `qy_question`）

## 🔧 开发工具

- **API文档**: Knife4j（Swagger增强版）
- **接口联调**: Apifox
- **代码生成**: MyBatis-Plus 代码生成器（待实现）

## 📈 后续规划

- ✅ **AI 智能出题 / 推荐功能**（结合 DeepSeek / GPT）
- ✅ **学习日报 / 每周总结自动生成**
- ✅ **面试模式模拟（限时答题 + 评分）**
- ✅ **打卡积分商城 / 勋章系统**
- ✅ **RAG 问答系统：题库知识问答**

## 📄 许可证

本项目采用 MIT 许可证。

## 👥 贡献者

- sherrylxf

---