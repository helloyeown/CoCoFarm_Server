package com.project.apiserver.boardfavorite.entity;

import com.project.apiserver.board.entity.Board;
import com.project.apiserver.member.entity.MemberAccount;

import groovy.transform.builder.Builder;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_board_favorite")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardFavorite {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bfno;

    @ManyToOne(fetch = FetchType.LAZY)
    MemberAccount member;

    @ManyToOne(fetch = FetchType.LAZY)
    Board board;
}
