# 1단계: 빌드 단계
FROM azul/zulu-openjdk-alpine:17-latest AS build
WORKDIR /app
COPY . .

RUN chmod +x ./gradlew
RUN ./gradlew clean build

# 2단계: 런타임 단계
FROM azul/zulu-openjdk-alpine:17-latest
COPY --from=build /app/build/libs/*.jar app.jar
COPY --from=build /app/build/generated-snippets /app/build/generated-snippets
COPY --from=build src/docs /app/docs

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080