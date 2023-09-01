package com.project.apiserver.review.entity;

import java.util.ArrayList;
import java.util.List;


import com.project.apiserver.common.BaseEntity;


import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.productboard.entity.Product;
import com.project.apiserver.productboard.entity.ProductImage;

import jakarta.persistence.ElementCollection;
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
import lombok.ToString;

@Entity
@Builder
@ToString(exclude = {"member", "images"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "tbl_review")
public class ProductReview extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;
    private String review;
    private boolean delFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    private MemberAccount member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

    public void addImage(String name) {

        ProductImage image = ProductImage.builder().fname(name)
                .ord(images.size()).build();

        images.add(image);
    }

    public void changeDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public void changeReply(String review) {
        this.review = review;
    }

    public void clearImages() {
        images.clear();
    }

}
