package com.project.apiserver.reply.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.reply.dto.ReplyDTO;
import com.project.apiserver.reply.dto.ReplyPageRequestDTO;
import com.project.apiserver.reply.entity.Reply;
import com.project.apiserver.reply.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ReplyServiceImpl extends Exception implements ReplyService{

    private final ReplyRepository replyRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<ReplyDTO> getReplyList(ReplyPageRequestDTO requestDTO) {

        boolean last = requestDTO.isLast();
        int pageNum = requestDTO.getPage(); 

        if (last) {
            long totalCount = replyRepository.getCountReply(requestDTO.getBno());

            pageNum = (int) (Math.ceil(totalCount / (double) requestDTO.getSize()));
            pageNum = pageNum <= 0 ? 1 : pageNum;
        }

        log.info("==================1111=================");



        Pageable pageable = PageRequest.of(pageNum - 1, requestDTO.getSize());

        log.info("==================2222=================");
        log.info(requestDTO.getBno());


        Page<ReplyDTO> result = replyRepository.getReplyList(requestDTO.getBno(), pageable);
        log.info(result.toString());

        log.info("==================3333=================");

        long totalReplyCount = result.getTotalElements();

        List<ReplyDTO> dtoList = result.stream().map(dto -> modelMapper.map(dto, ReplyDTO.class))
                .collect(Collectors.toList());
        log.info(dtoList);
        PageResponseDTO<ReplyDTO> responseDTO = new PageResponseDTO<>(dtoList, totalReplyCount, requestDTO);
        responseDTO.setPage(pageNum);


        

        return responseDTO;

    }

    @Override
    public Long registReply(ReplyDTO replyDTO) {

        log.info("==========33333==============");

        log.info(replyDTO.getGno());
        log.info(replyDTO.isOrd());

        Reply reply = modelMapper.map(replyDTO, Reply.class);

        MemberAccount memberAccount = MemberAccount.builder().mno(replyDTO.getMno()).build();

        reply.setMember(memberAccount);
       
        replyRepository.save(reply);
        Boolean changeOrd= !replyDTO.isOrd();
        log.info(changeOrd);
        log.info(reply);
        if(replyDTO.getGno()!=null){
            reply.changeOrd(changeOrd);
            replyDTO.setGno(reply.getGno());
            replyRepository.save(reply);
        }
        else{
            replyDTO.setOrd(replyDTO.isOrd());
            replyDTO.setGno(reply.getRno());
            reply.setGno(reply.getRno());
            replyRepository.save(reply);
        }
        replyDTO.setRno(reply.getRno());
       
        return replyDTO.getRno();

    }

    // 댓글 삭제
    @Override
    public Long deleteReply(Long rno) {

        Optional<Reply> result = replyRepository.findById(rno);

        Reply reply = result.orElseThrow();

        reply.changeDelFlag(true);
        reply.changeReply("삭제되었습니다.");
        

        replyRepository.save(reply);

        return rno;

    }

    // 댓글 수정
    @Override
    public Long modifyReply(ReplyDTO replyDTO) {

        Optional<Reply> result = replyRepository.findById(replyDTO.getRno());

        Reply reply = result.orElseThrow();

        // 삭제된 댓글은 수정을 할 수 없다.

        log.info("===============================================" + replyDTO.isDelFlag());
        if(replyDTO.isDelFlag()){
            throw new RuntimeException("삭제된 댓글 입니다.");
        }

        reply.changeReply(replyDTO.getReply());
        
        replyRepository.save(reply);

        return replyDTO.getRno();

    }

    @Override
    public ReplyDTO readOneReply(Long rno) {
        log.info("----------------------------------");
        return replyRepository.getOneReply(rno);
    }

}
