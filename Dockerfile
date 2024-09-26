# 1단계: 빌드 단계
FROM azul/zulu-openjdk-alpine:17-latest AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test
RUN ls -l /app/build/libs

# 2단계: 런타임 단계
FROM azul/zulu-openjdk-alpine:17-latest
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080