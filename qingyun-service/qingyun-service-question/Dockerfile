FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

COPY pom.xml .
COPY qingyun-service/pom.xml ./qingyun-service/
COPY qingyun-common/pom.xml ./qingyun-common/
COPY qingyun-api/pom.xml ./qingyun-api/
COPY qingyun-service/qingyun-service-question/pom.xml ./qingyun-service/qingyun-service-question/

RUN mvn dependency:go-offline -B

COPY qingyun-common/src ./qingyun-common/src
COPY qingyun-api/src ./qingyun-api/src
COPY qingyun-service/qingyun-service-question/src ./qingyun-service/qingyun-service-question/src

RUN mvn clean package -DskipTests -pl qingyun-service/qingyun-service-question -am

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=build /app/qingyun-service/qingyun-service-question/target/*.jar app.jar

RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    echo "Asia/Shanghai" > /etc/timezone

EXPOSE 8002

ENTRYPOINT ["java", "-jar", "app.jar"]

