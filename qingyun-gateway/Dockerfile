# 多阶段构建
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# 复制pom文件
COPY pom.xml .
COPY qingyun-common/pom.xml ./qingyun-common/
COPY qingyun-api/pom.xml ./qingyun-api/
COPY qingyun-gateway/pom.xml ./qingyun-gateway/

# 下载依赖
RUN mvn dependency:go-offline -B

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

