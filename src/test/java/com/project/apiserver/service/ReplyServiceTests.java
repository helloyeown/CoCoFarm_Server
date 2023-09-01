package com.project.apiserver.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.apiserver.reply.dto.ReplyDTO;
import com.project.apiserver.reply.dto.ReplyPageRequestDTO;
import com.project.apiserver.reply.service.ReplyService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ReplyServiceTests {
    
    @Autowired
    private ReplyService replyService;

    @Test
    @Transactional
    public void insertTest(){

        //ReplyDTO dto = ReplyDTO.builder().bno(100L).ord(false).reply("대댓글").email("aaa100@email.com").build();
        //replyService.registReply(dto);

    }

    @Test 
    public void readListTest(){

        ReplyPageRequestDTO requestDTO = ReplyPageRequestDTO.builder().bno(100L).last(true).build();
        replyService.getReplyList(requestDTO);
        log.info(replyService.getReplyList(requestDTO));

    }

    @Test
    public void deleteReplyTest(){

        replyService.deleteReply(8L);

    }

    @Test
    public void modifyReplyTest(){

        ReplyDTO replyDTO = ReplyDTO.builder()
        .rno(9L)
        .reply("수정하였습니다.")
        .build();

        replyService.modifyReply(replyDTO);

    }

}
