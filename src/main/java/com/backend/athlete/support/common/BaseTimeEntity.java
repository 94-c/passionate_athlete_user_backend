package com.backend.athlete.support.common;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "create_date", updatable = false)
    private String createdDate;

    @Column(name = "modified_date")
    private String modifiedDate;

    @PrePersist
    public void onPrePersist(){
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.createdAt = now.toLocalDateTime();
        this.modifiedAt = now.toLocalDateTime();
        this.createdDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.modifiedDate = this.createdDate;
    }

    @PreUpdate
    public void onPreUpdate(){
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.modifiedAt = now.toLocalDateTime();
        this.modifiedDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
