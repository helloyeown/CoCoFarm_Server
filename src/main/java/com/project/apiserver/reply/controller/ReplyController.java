package com.project.apiserver.reply.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.reply.dto.ReplyDTO;
import com.project.apiserver.reply.dto.ReplyPageRequestDTO;
import com.project.apiserver.reply.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/replies/")
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("{bno}/list")
    public PageResponseDTO<ReplyDTO> repliseList(@PathVariable("bno") Long bno, ReplyPageRequestDTO requestDTO) {
        
        log.info(replyService.getReplyList(requestDTO));
        return replyService.getReplyList(requestDTO);

    }

    @GetMapping("{rno}")
    public ReplyDTO getOneReply(@PathVariable("rno") Long rno){
        log.info("rno=-----------------------------------");
        log.info(rno);
        return replyService.readOneReply(rno);
    }


    // 등록
    @PostMapping("")
    public Map<String, Long> registReply(@RequestBody ReplyDTO replyDTO) {
 
        log.info("--------------111-------------");
        log.info("--------------replyDTO------------- insert test");
        log.info(replyDTO.getGno());
        replyService.registReply(replyDTO);

        log.info("--------------222-------------");

        return Map.of("result", replyDTO.getRno());

    }

    // 삭제
    @DeleteMapping("{rno}")
    public Map<String, Long> deleteReply(@PathVariable("rno") Long rno) {

        log.info("Delete ........................ reply...........");

        replyService.deleteReply(rno);

        return Map.of("result", rno);

    }

    // 댓글 수정
    @PutMapping("")
    public Map<String, Long> modifyReply(@RequestBody ReplyDTO replyDTO) {

 
        replyService.modifyReply(replyDTO);

        return Map.of("result", replyDTO.getRno());

    }

}
