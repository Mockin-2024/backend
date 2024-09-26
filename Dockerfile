# 1단계: 빌드 단계
FROM openjdk:17 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# 2단계: 런타임 단계
FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
