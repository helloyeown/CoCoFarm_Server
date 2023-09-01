package com.project.apiserver.reply.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.apiserver.reply.dto.ReplyDTO;
import com.project.apiserver.reply.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    // Object[] 배열 대신 dto로 받는 쿼리
    @Query("select new com.project.apiserver.reply.dto.ReplyDTO(r.rno, r.reply,r.gno, r.ord, r.delFlag,  r.member.mno,r.member.email, r.member.nickname, r.board.bno, r.regDate, r.modDate) from Reply r left join MemberAccount m on m.mno = r.member.mno where r.board.bno = :bno group by r.rno order by r.gno asc")
    Page<ReplyDTO> getReplyList(@Param("bno") Long bno, Pageable pageable);

    // 댓글 수 카운트
    @Query("select count(r) from Reply r where r.board.bno = :bno")
    long getCountReply(@Param("bno") Long bno);

    @Query("select new com.project.apiserver.reply.dto.ReplyDTO(r.rno, r.reply,r.gno, r.ord, r.delFlag,  r.member.mno,r.member.email, r.member.nickname, r.board.bno, r.regDate, r.modDate) from Reply r left join MemberAccount m on m.mno = r.member.mno where r.delFlag = false and r.rno = :rno group by  r.rno order by r.gno, r.ord asc")
    ReplyDTO getOneReply(@Param("rno") Long rno);

  
}
