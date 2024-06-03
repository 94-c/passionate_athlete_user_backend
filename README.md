# Passionate_Athlete (운동 기록 웹 프로젝트)

------------------------- 

## Development Stack

-------------------------

- Language : Java 17
- Framework : Spring Boot 3.2.*
- Database : MySQL


## Setting

-------------------------

~~~ 
debug: false

logging:
  level:
    com.backend.mes: DEBUG
    org.springframework.web.servlet: DEBUG
    org.hibernate.type.descriptor.sql.BasicBinder: TRACE
    org.springframework.web.client.RestTemplate: DEBUG

spring:
  datasource:
    url: 
    username: 
    password:
    driver-class-name: 
  jpa:
    open-in-view: false
    defer-datasource-initialization: true
    hibernate:
      ddl-auto: update
      show-sql: true
      properties:
        hibernate:
          format_sql: true
          dialect: 
  sql.init.mode: always
  data.rest:
    base-path: /api
    detection-strategy: annotated
server:
  port: 9081

springdoc:
  swagger-ui:
    path: /swagger-ui.html
    groups-order: DESC
    operationsSorter: method
    disable-swagger-default-url: true
    display-request-duration: true
  api-docs:
    path: /api-docs
  show-actuator: true
  default-consumes-media-type: application/json
  default-produces-media-type: application/json
  paths-to-match:
    - /v1/**

jwt:
  header: 
  prefix: 
  secret: 
  expiration_time: 

