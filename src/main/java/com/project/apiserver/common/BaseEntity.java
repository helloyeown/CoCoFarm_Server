package com.project.apiserver.common;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

// 추상클래스로 아예
// 테이블로 만들어 지지말라 거는 annotation
@MappedSuperclass
@Getter
// 테이블 설정시 자동으로 들어가게 해준다. 
@EntityListeners(value = { AuditingEntityListener.class })
public abstract class BaseEntity {
    
    //JAVA8버전 타입 API가 생김
    // LocalDate는 날짜까지 time붙으면 시분초
    
    @CreatedDate
    @Column
    private LocalDateTime regDate;

    @LastModifiedDate
    private LocalDateTime modDate;
}