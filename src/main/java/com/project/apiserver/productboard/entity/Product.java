package com.project.apiserver.productboard.entity;


import java.util.ArrayList;
import java.util.List;

import com.project.apiserver.common.BaseEntity;
import com.project.apiserver.common.ProductCategory;
import com.project.apiserver.member.entity.MemberAccount;

import jakarta.persistence.Column;
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
@Getter
@Table(name = "tbl_product")
@ToString(exclude = {"images","member","category"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pno;

    private String pname;
    private String pdesc;

    @ManyToOne(fetch =  FetchType.LAZY)
    private MemberAccount member;

    private int price;
    // column이 되니 조심하게 만들어야 된다.
    // delFlag
    private boolean delFlag;
    @Column(columnDefinition = "integer default 0")
    private Integer view;
    // ElementCollection은 종속적인 요소까지 함께 삭제 됨 (cascade 불필요)
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    private ProductCategory category;

    // 상품을 추가하는 method
    public void addImage(String name) {

        ProductImage pImage = ProductImage.builder().fname(name)
                .ord(images.size()).build();

        images.add(pImage);
    }

    // 이미지 파일들을 싹 비워주는 method
    public void clearImages() {
        images.clear();
    }

    public void changePrice(int price) {
        this.price = price;
    }

    public void changePname(String pname) {
        this.pname = pname;
    }

    public void changePdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public void changeProductCategory(ProductCategory category) {
        this.category = category;
    }

}
