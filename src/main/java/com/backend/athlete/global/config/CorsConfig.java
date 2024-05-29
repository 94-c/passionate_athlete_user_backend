package com.backend.athlete.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        // 특정 출처(origin)에서 오는 요청을 허용
                        .allowedOrigins("http://localhost:3000")  // 프론트엔드 URL로 변경
                        // 허용할 HTTP 메소드 설정
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // 허용할 HTTP 헤더 설정
                        .allowedHeaders("*")
                        // 자격 증명(쿠키, 인증 정보)을 포함한 요청 허용
                        .allowCredentials(true);
            }
        };
    }
}
