package com.project.apiserver.boardfavorite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.apiserver.boardfavorite.entity.BoardFavorite;

import jakarta.transaction.Transactional;

public interface BoardFavoriteRepository extends JpaRepository<BoardFavorite, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO tbl_board_favorite (board_bno, member_mno) VALUES (:bno, :mno)", nativeQuery = true)
    void insertSubscriptionNative(@Param("bno") Long bno, @Param("mno") Long mno);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM tbl_board_favorite WHERE board_bno = :bno AND member_mno = :mno", nativeQuery = true)
    void deleteSubscriptionNative(@Param("bno") Long bno, @Param("mno") Long mno);
    

    @Query(value = "SELECT  COUNT(b) FROM BoardFavorite b WHERE b.board.bno = :bno GROUP BY b.board.bno")
    Long countSub(@Param("bno") Long bno);

    @Query(value = "SELECT count(*) from tbl_board_favorite where board_bno = :bno and member_mno = :mno", nativeQuery = true)
    Long checkFavorite(@Param("bno") Long bno, @Param("mno") Long mno);
}
