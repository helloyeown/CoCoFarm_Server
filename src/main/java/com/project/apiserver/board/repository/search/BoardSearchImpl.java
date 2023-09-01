package com.project.apiserver.board.repository.search;

import java.util.List;

import com.project.apiserver.member.entity.QMemberAccount;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import com.project.apiserver.board.dto.BoardListDTO;
import com.project.apiserver.board.entity.Board;
import com.project.apiserver.board.entity.QBoard;
import com.project.apiserver.board.entity.QBoardImage;
import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.common.QCategory;

import com.project.apiserver.reply.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

    public BoardSearchImpl() {
        super(Board.class);
    }

    @Override
    public PageResponseDTO<BoardListDTO> search(PageRequestDTO pageRequestDTO) {

        Pageable pageable = makePageable(pageRequestDTO);

        QBoard qBoard = QBoard.board;
        QMemberAccount qMember = QMemberAccount.memberAccount;
        QCategory qCategory = QCategory.category;
        QReply qReply = QReply.reply1;


        String keyword = pageRequestDTO.getKeyword();
        String searchType = pageRequestDTO.getType();
        Integer cateno1 = pageRequestDTO.getCateno();
        log.info(cateno1);

        JPQLQuery<Board> searchQuery = from(qBoard);

        log.info("--------------------------------------1");

        searchQuery.leftJoin(qBoard.category, qCategory);
        searchQuery.leftJoin(qBoard.member, qMember);
        searchQuery.leftJoin(qReply).on(qReply.board.eq(qBoard));

        log.info("--------------------------------------2");

        searchQuery.where(qBoard.category.cateno.eq(cateno1));
        searchQuery.where(qBoard.delFlag.eq(Boolean.FALSE));


        log.info("-------------------------------------3");
        if (keyword != null && searchType != null) {
            // tc => [t,c]
            String[] searchArr = searchType.split("");
            // BooleanBuilder 생성
            BooleanBuilder searchBuilder = new BooleanBuilder();
            for (String typeword : searchArr) {

                switch (typeword) {
                    case "t" -> searchBuilder.or(qBoard.title.contains(keyword));
                    case "c" -> searchBuilder.or(qBoard.content.contains(keyword));
                    case "w" -> searchBuilder.or(qBoard.member.nickname.contains(keyword));
                }

            } // end for
              // for문 끝난후 where 로 searchBuilder 추가
            searchQuery.where(searchBuilder);
        }

        log.info("--------------------------------------");
        this.getQuerydsl().applyPagination(pageable, searchQuery);

        searchQuery.groupBy(qBoard);

        JPQLQuery<BoardListDTO> listQuery = searchQuery.select(Projections.bean(
                BoardListDTO.class,
                qBoard.bno,
                qBoard.title,
                qBoard.content,
                qMember.email,
                qMember.nickname,
                qBoard.category.catename,
                qBoard.category.cateno,
                qBoard.regDate,
                qBoard.view,

                qReply.countDistinct().as("rcnt")));

        long totalCount = listQuery.fetchCount();
        List<BoardListDTO> list = listQuery.fetch();
        log.info(list);

        return new PageResponseDTO<>(list, totalCount, pageRequestDTO);

    }

    @Override
    public PageResponseDTO<BoardListDTO> searchSameWriter(Long mno, PageRequestDTO pageRequestDTO) {
        Pageable pageable = makePageable(pageRequestDTO);

        QBoard qBoard = QBoard.board;
        QMemberAccount qMember = QMemberAccount.memberAccount;
        QCategory qCategory = QCategory.category;
        QReply qReply = QReply.reply1;


        String keyword = pageRequestDTO.getKeyword();
        String searchType = pageRequestDTO.getType();
        Integer cateno1 = pageRequestDTO.getCateno();
        log.info(cateno1);
        
        JPQLQuery<Board> searchQuery = from(qBoard);

        log.info("--------------------------------------1");

        searchQuery.leftJoin(qBoard.category, qCategory);
        searchQuery.leftJoin(qBoard.member, qMember);

        searchQuery.leftJoin(qReply).on(qReply.board.eq(qBoard));

        log.info("--------------------------------------2");

        searchQuery.where(qBoard.category.cateno.eq(cateno1));
        searchQuery.where(qBoard.member.mno.eq(mno));
        searchQuery.where(qBoard.delFlag.eq(Boolean.FALSE));


        log.info("-------------------------------------3");
        if (keyword != null && searchType != null) {
            // tc => [t,c]
            String[] searchArr = searchType.split("");
            // BooleanBuilder 생성
            BooleanBuilder searchBuilder = new BooleanBuilder();
            for (String typeword : searchArr) {

                switch (typeword) {
                    case "t" -> searchBuilder.or(qBoard.title.contains(keyword));
                    case "c" -> searchBuilder.or(qBoard.content.contains(keyword));
                }

            } // end for
              // for문 끝난후 where 로 searchBuilder 추가
            searchQuery.where(searchBuilder);
        }

        log.info("--------------------------------------");
        this.getQuerydsl().applyPagination(pageable, searchQuery);

        searchQuery.groupBy(qBoard);
     
        JPQLQuery<BoardListDTO> listQuery = searchQuery.select(Projections.bean(
                BoardListDTO.class,
                qBoard.bno,
                qBoard.title,
                qBoard.content,
                qMember.email,
                qMember.nickname,
                qBoard.category.catename,
                qBoard.category.cateno,
                qBoard.regDate,
                qBoard.view,
  
                qReply.countDistinct().as("rcnt")));

        long totalCount = listQuery.fetchCount();
        List<BoardListDTO> list = listQuery.fetch();
        log.info(list);

        return new PageResponseDTO<>(list, totalCount, pageRequestDTO);

    }

}
