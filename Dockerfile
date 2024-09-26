# 1단계: 빌드 단계
FROM azul/zulu-openjdk-alpine:17-latest AS build
WORKDIR /app

# Gradle Wrapper 및 의존성 파일만 먼저 복사하여 캐시 활용
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle settings.gradle ./

# Gradle 의존성 다운로드
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY src ./src

# 빌드 실행
RUN ./gradlew clean build -x test --no-daemon

# 2단계: 런타임 단계
FROM azul/zulu-openjdk-alpine:17-latest
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080


## 1단계: 빌드 단계
#FROM azul/zulu-openjdk-alpine:17-latest AS build
#WORKDIR /app
#COPY . .
#
#RUN chmod +x ./gradlew
#RUN ./gradlew clean build -x test
#
## 2단계: 런타임 단계
#FROM azul/zulu-openjdk-alpine:17-latest
#COPY --from=build /app/build/libs/*.jar app.jar
#
#ENTRYPOINT ["java","-jar","/app.jar"]
#EXPOSE 8080