package com.project.apiserver.productfavorite.entity;

import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.productboard.entity.Product;

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
@Table(name = "tbl_product_favorite")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFavorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pfno;
    @ManyToOne(fetch = FetchType.LAZY)
    MemberAccount member;
    @ManyToOne(fetch = FetchType.LAZY)
    Product product;

}
