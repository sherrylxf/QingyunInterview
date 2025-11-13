# 多阶段构建
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# 复制所有pom文件
COPY pom.xml .
COPY qingyun-common/pom.xml ./qingyun-common/
COPY qingyun-api/pom.xml ./qingyun-api/
COPY qingyun-gateway/pom.xml ./qingyun-gateway/
COPY qingyun-auth/pom.xml ./qingyun-auth/
COPY qingyun-web-admin/pom.xml ./qingyun-web-admin/
COPY qingyun-web-user/pom.xml ./qingyun-web-user/
COPY qingyun-service/pom.xml ./qingyun-service/
COPY qingyun-service/qingyun-service-user/pom.xml ./qingyun-service/qingyun-service-user/
COPY qingyun-service/qingyun-service-question/pom.xml ./qingyun-service/qingyun-service-question/
COPY qingyun-service/qingyun-service-record/pom.xml ./qingyun-service/qingyun-service-record/
COPY qingyun-service/qingyun-service-admin/pom.xml ./qingyun-service/qingyun-service-admin/
COPY qingyun-service/qingyun-service-stat/pom.xml ./qingyun-service/qingyun-service-stat/

# 创建必要的目录结构
RUN mkdir -p qingyun-common/src/main/java qingyun-common/src/main/resources && \
    mkdir -p qingyun-api/src/main/java qingyun-api/src/main/resources && \
    mkdir -p qingyun-gateway/src/main/java qingyun-gateway/src/main/resources && \
    mkdir -p qingyun-auth/src/main/java qingyun-auth/src/main/resources && \
    mkdir -p qingyun-web-admin/src/main/java qingyun-web-admin/src/main/resources && \
    mkdir -p qingyun-web-user/src/main/java qingyun-web-user/src/main/resources && \
    mkdir -p qingyun-service/qingyun-service-user/src/main/java qingyun-service/qingyun-service-user/src/main/resources && \
    mkdir -p qingyun-service/qingyun-service-record/src/main/java qingyun-service/qingyun-service-record/src/main/resources && \
    mkdir -p qingyun-service/qingyun-service-question/src/main/java qingyun-service/qingyun-service-question/src/main/resources && \
    mkdir -p qingyun-service/qingyun-service-admin/src/main/java qingyun-service/qingyun-service-admin/src/main/resources && \
    mkdir -p qingyun-service/qingyun-service-stat/src/main/java qingyun-service/qingyun-service-stat/src/main/resources

# 下载依赖（只针对gateway模块及其依赖）
RUN mvn dependency:go-offline -B -pl qingyun-gateway -am

# 复制源代码
COPY qingyun-common/src ./qingyun-common/src
COPY qingyun-api/src ./qingyun-api/src
COPY qingyun-gateway/src ./qingyun-gateway/src

# 构建项目
RUN mvn clean package -DskipTests -pl qingyun-gateway -am

# 运行阶段
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# 复制构建产物
COPY --from=build /app/qingyun-gateway/target/*.jar app.jar

# 设置时区
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

# 暴露端口
EXPOSE 8080

# 启动应用
ENTRYPOINT ["java", "-jar", "app.jar"]

