package com.project.apiserver.board.entity;

import java.util.ArrayList;
import java.util.List;

import com.project.apiserver.common.BaseEntity;
import com.project.apiserver.common.Category;


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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"member","category", "images"})
@Table(name = "tbl_board")
public class Board extends BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;
    private String title;
    private String content;

    private boolean delFlag;
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberAccount member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;

    // ElementCollection은 종속적인 요소까지 함께 삭제 됨 (cascade 불필요)
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private List<BoardImage> images = new ArrayList<>();

    @Column(columnDefinition = "integer default 0")
    private Integer view;

    // 상품을 추가하는 method
    public void addImage(String name) {

        BoardImage bImage = BoardImage.builder().fname(name)
                .ord(images.size()).build();

        images.add(bImage);
    }
    public void clearImages() {
        images.clear();
    }

    public void changeDelFlag(boolean delFlag) {
        this.delFlag = delFlag;
    }
    public void changeTitle(String title){
        this.title = title;
    }
    public void changeContent(String content){
        this.content = content;
    }
    
}
