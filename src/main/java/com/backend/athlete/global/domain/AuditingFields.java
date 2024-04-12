package com.backend.athlete.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class AuditingFields {

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Comment("생성일")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "modified_at")
    @Comment("수정일")
    private LocalDateTime modifiedAt;

}
