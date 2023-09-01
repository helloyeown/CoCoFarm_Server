package com.project.apiserver.repository;

import com.project.apiserver.member.entity.MemberAccount;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import com.project.apiserver.board.dto.BoardListDTO;
import com.project.apiserver.board.dto.BoardReadDTO;
import com.project.apiserver.board.entity.Board;
import com.project.apiserver.board.repository.BoardRepository;
import com.project.apiserver.common.Category;
import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;


import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void addBoard() {

        Category category = Category
        .builder()
        .cateno(1)
        .build();

        MemberAccount member = MemberAccount.builder().mno(4L).build();
        
        log.info("Start insert");

        for (int i = 0; i < 10; i++) {

            Board board = Board.builder()
                    .category(category)
                    .member(member)
                    .title("상품 문의")
                    .content("상품 문의합니다.")
                    .build();

            boardRepository.save(board);

        }

        log.info("------------------------------");
        log.info("End Insert");

    }
    @Test
    public void addNotice(){
        Category category = Category
        .builder()
        .cateno(5)
        .catename("공지사항")
        .build();
        MemberAccount member = MemberAccount.builder().mno(11L).build();
        for(int i = 0; i<9; i++){
        Board board =Board.builder()
        .category(category)
        .member(member)
        .title("공지사항입니다~" +i)
        .content("차은우가 하고있어요" +i)
        .build();

        boardRepository.save(board);
        }
    }



    
    @Test
    public void searchTest(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10, "c", "1", 1);

        PageResponseDTO<BoardListDTO> responseDTO = boardRepository.search(pageRequestDTO);

        log.info(responseDTO);

    }

    @Test
    public void searchWriterTest(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, 10,null,null,2);

        PageResponseDTO<BoardListDTO> responseDTO = boardRepository.searchSameWriter(3L, pageRequestDTO);
        log.info(responseDTO);
    }


    @Test
    @Transactional
    public void getBoardOne(){


        boardRepository.incrementView(311L);
       List<BoardReadDTO> data = boardRepository.selectOne(311L); 
   
       log.info(data);

    }
    @Test
    public void checkView(){
        log.info("1234544444444444444");
        boardRepository.incrementView(11L);
        log.info("SSSSSSSSSSSSSSSSSSS");
    }

    @Test
    public void addDummy(){
        Category category = Category.builder().cateno(3).build();
        MemberAccount memberAccount = MemberAccount.builder().mno(480L).build(); 
        for(int i = 0; i < 100; i++){
            Board board = Board.builder()
            .category(category)
            .member(memberAccount)
            .title("농부의 재배일지" +i)
            .content("재배일지 스타트 ~~~~" +i)
            .build();

            boardRepository.save(board);
        }
    }
    @Test
    public void addTestWithFile(){
        Category category = Category.builder().cateno(4).build();
        MemberAccount memberAccountTest = MemberAccount.builder().mno(480L).build(); 

        Board board = Board.builder()
        .category(category)
        .member(memberAccountTest)
        .title("자유게시판글")
        .content("자유게시글~~")
        .view(0)
        .build();
        board.addImage(UUID.randomUUID().toString()+"aaa.jpg");
        boardRepository.save(board);
    }

    // song
    // 등록
    @Test
    public void insertImageTest(){

        Category category = Category
        .builder()
        .cateno(2)
        .catename("재배일지")
        .build();
        
        MemberAccount member = MemberAccount.builder().mno(3L).build();

        Board board = Board.builder()
        .bno(312L)
        .category(category)
        .member(member)
        .title("보드 이미지등록테스트~")
        .content("SongMK" )
        .build();
            
            board.addImage(UUID.randomUUID().toString()+ "_aaa.jpg");
            // board.addImage(UUID.randomUUID().toString()+"_bbb.jpg");
            // board.addImage(UUID.randomUUID().toString()+"_ccc.jpg");

            boardRepository.save(board);
        }

    
}




