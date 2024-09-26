# 1단계: 빌드 단계
FROM azul/zulu-openjdk-alpine:17-latest AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

# 2단계: 런타임 단계
FROM azul/zulu-openjdk-alpine:17-latest

ARG JAR_FILE=/app/build/libs/mockin.jar
COPY --from=build ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080