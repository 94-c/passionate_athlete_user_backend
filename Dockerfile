# 1단계: 빌드 단계
FROM gradle:7.6.0-jdk17 AS build
WORKDIR /app

# 필요한 파일들 복사
COPY build.gradle settings.gradle /app/
COPY src /app/src

# 필요한 환경 변수들을 빌드 아규먼트로 받기
ARG DB_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG JWT_SECRET

# 환경 변수 설정 (빌드 시점에 사용, 필요시 테스트 스킵)
RUN gradle clean build -x test

# 2단계: 실행 단계
FROM openjdk:17-jdk-slim
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /app/build/libs/*.jar /app/app.jar

# 런타임 환경 변수 설정
ENV DB_URL=$DB_URL
ENV DB_USERNAME=$DB_USERNAME
ENV DB_PASSWORD=$DB_PASSWORD
ENV JWT_SECRET=$JWT_SECRET

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
