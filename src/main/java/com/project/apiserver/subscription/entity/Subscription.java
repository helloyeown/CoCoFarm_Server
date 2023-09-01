package com.project.apiserver.subscription.entity;

import com.project.apiserver.member.entity.MemberAccount;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_subscription")
public class Subscription {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subno;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberAccount fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberAccount toAccount;
}
