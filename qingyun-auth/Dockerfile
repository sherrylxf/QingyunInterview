FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY qingyun-common/pom.xml ./qingyun-common/
COPY qingyun-api/pom.xml ./qingyun-api/
COPY qingyun-auth/pom.xml ./qingyun-auth/

RUN mvn dependency:go-offline -B

COPY qingyun-common/src ./qingyun-common/src
COPY qingyun-api/src ./qingyun-api/src
COPY qingyun-auth/src ./qingyun-auth/src

RUN mvn clean package -DskipTests -pl qingyun-auth -am

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/qingyun-auth/target/*.jar app.jar

RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "app.jar"]

