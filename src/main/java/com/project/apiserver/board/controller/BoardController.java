package com.project.apiserver.board.controller;

import java.util.Map;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.apiserver.board.dto.BoardDTO;
import com.project.apiserver.board.dto.BoardListDTO;
import com.project.apiserver.board.dto.BoardReadDTO;
import com.project.apiserver.board.service.BoardService;
import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController

@RequiredArgsConstructor
@RequestMapping("/api/board/")

@Log4j2
public class BoardController {

    private final BoardService boardService;

    // 하나만
    @GetMapping("{bno}")
    public BoardReadDTO getOne(@PathVariable("bno") Long bno) {
        log.info("----------------------------------");
        log.info(bno);

        return boardService.getOne(bno);
    }

    // 전체 리스트 및 검색
    @GetMapping("list")
    public PageResponseDTO<BoardListDTO> getList(
            // queryString 값으로 받아옴
            @ParameterObject PageRequestDTO pageRequestDTO) {
                
        pageRequestDTO.setCategory(
               pageRequestDTO.getCateno() == null || pageRequestDTO.getCateno() <= 0 ? 5 : pageRequestDTO.getCateno());
        return boardService.getList(pageRequestDTO);
    }

    @GetMapping("list/{mno}")
    public PageResponseDTO<BoardListDTO> getListSameWriter(
        @PathVariable("mno") Long mno,
        @ParameterObject PageRequestDTO pageRequestDTO
    ){
        pageRequestDTO.setCategory(
            pageRequestDTO.getCateno() == null || pageRequestDTO.getCateno() <= 0 ? 5 : pageRequestDTO.getCateno());
        
        return boardService.getListSameWriter(mno, pageRequestDTO);
    }


    // 게시글 등록
    @PostMapping("")
    public Map<String, Long> registBoard(BoardDTO boardDTO) {
        log.info("testsdasdasdadasdsadasdadsa");
        log.info(boardDTO);
        boardService.registBoard(boardDTO);
        log.info(boardDTO);

        return Map.of("result", boardDTO.getBno());
    }

    // 게시글 삭제
    @DeleteMapping("{bno}")
    public Map<String, Long> deleteBoard(@PathVariable("bno") Long bno) {

        boardService.deleteBoard(bno);

        return Map.of("result", bno);
    }

    // 게시글 수정
    @PutMapping("")
    public Map<String, Long> modifyBoard(BoardDTO boardDTO) {

        boardService.modifyBoard(boardDTO);

        return Map.of("result", boardDTO.getBno());
    }
}
