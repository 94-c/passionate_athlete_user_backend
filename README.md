# Passionate_Athlete (운동 기록 웹 프로젝트)

이 리포지토리는 `Passionate Athlete` 애플리케이션의 회원 백엔드 API 서비스 입니다.


## Development Stack


- Language : Java 17
- Framework : Spring Boot 3.2.*
- Database : MySQL

## Project Architecture

- 레이어드 아키텍처(Layed Architecture)로 하여 패키지를 설계 하였으며, 각 책임 영역을 분리, 유지보수를 용이하게 하기 위하여 만들었습니다.

```
src/
├── main/
│   ├── java/com/backend/athlete
│   │   │   ├── domain/
│   │   │   │   ├── application/
│   │   │   │   ├── controller/
│   │   │   │   ├── domain/
│   │   │   │   ├── dto/
│   │   │   │   └── infrastructure/
│   └── resources/
│       └── application.yaml
└── test/
    └── java/com/passionateathlete/
```

## Architecture 구조 요약

- **application**: 애플리케이션 서비스 계층으로, 도메인 로직을 조합하여 구현된 비즈니스 로직을 제공합니다. 이곳의 클래스들은 주로 도메인 객체를 사용하여 비즈니스 프로세스를 처리합니다.

- **controller**: 프레젠테이션 계층으로, REST API의 엔드포인트를 정의하고, 클라이언트 요청을 처리합니다. 각 컨트롤러는 특정 도메인과 관련된 요청을 수신하고, 서비스를 호출하여 필요한 작업을 수행합니다.

- **domain** : 핵심 도메인 객체와 애그리게이트, 엔티티를 정의하는 곳입니다. 비즈니스 규칙이 포함된 중요한 객체들이 위치하며, 도메인의 불변성과 일관성을 유지하는 역할을 합니다.

- **dto** : 데이터 전송 객체(Data Transfer Object)를 정의합니다. 이 객체들은 컨트롤러와 서비스 계층 간에 데이터가 전달될 때 사용됩니다. 주로 클라이언트로부터의 요청이나 응답을 캡슐화하는 데 사용됩니다.

- **infrastructure** : 데이터베이스, 파일 시스템, 외부 API 등과 같은 외부 인프라스트럭처와의 상호작용을 담당합니다. JPA 리포지토리나 외부 서비스 클라이언트 등을 정의합니다

## 설치 및 실행 방법

```
git clone https://github.com/yourusername/passionate-athlete.git
```

```
./mvnw clean install
```
```
./mvnw spring-boot:run
```
