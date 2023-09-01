package com.project.apiserver.service;// package com.project.apiserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.apiserver.board.dto.BoardDTO;
import com.project.apiserver.board.service.BoardService;
import com.project.apiserver.common.PageRequestDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void getListOneTest(){

        PageRequestDTO dto = new PageRequestDTO(1, 10, null, null, 2);

        log.info(boardService.getListSameWriter(3L, dto));

    }

//     @Test
//     public void getList(){


//         // log.info(boardService.());

//     }

//     //삭제 테스트
//     @Test
//     public void deleteBoardTest(){

//         boardService.deleteBoard(8L);

//     }

//     //수정 테스트
//     @Test
//     public void ModifyBoardTest(){

//         BoardReadDTO boardReadDTO = BoardReadDTO.builder()
//         .bno(8L)
//         .content("수정된 게시글 입니다.")
//         .build();

//         boardService.modifyBoard(boardReadDTO);

//     }

    @Test
    public void insertBoardTest(){

                List<String> images = new ArrayList<>();

        images.add(UUID.randomUUID().toString() + "_aaa.jpg");
        images.add(UUID.randomUUID().toString() + "_bbb.jpg");
        images.add(UUID.randomUUID().toString() + "_ccc.jpg");

        BoardDTO boardDTO = BoardDTO.builder()
        .cateno(4)
        .mno(8L)
        .title("tests111")
        .content("test contentss  11")
        .images(images)
        .view(0)
        .build();
        boardService.registBoard(boardDTO);
    }
}
