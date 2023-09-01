package com.project.apiserver.board.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.apiserver.board.dto.BoardDTO;
import com.project.apiserver.board.dto.BoardListDTO;
import com.project.apiserver.board.dto.BoardReadDTO;
import com.project.apiserver.board.entity.Board;
import com.project.apiserver.board.repository.BoardRepository;
import com.project.apiserver.common.Category;
import com.project.apiserver.common.FileUploader;
import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.member.entity.MemberAccount;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final FileUploader fileUploader;

    @Override
    public PageResponseDTO<BoardListDTO> getList(PageRequestDTO pageRequestDTO) {
        
        return boardRepository.search(pageRequestDTO);
    }

    @Override
    public BoardReadDTO getOne(Long bno) {
        boardRepository.incrementView(bno);
        log.info("GET read................................");
        List<BoardReadDTO> readList =boardRepository.selectOne(bno);
        
        BoardReadDTO dto = BoardReadDTO
        .builder()
        .bno(readList.get(0).getBno())
        .title(readList.get(0).getTitle())
        .content(readList.get(0).getContent())
        .email(readList.get(0).getEmail())
        .nickname(readList.get(0).getNickname())
        .catename(readList.get(0).getCatename())
        .cateno(readList.get(0).getCateno())
        .mno(readList.get(0).getMno())
        .regDate(readList.get(0).getRegDate())
        .modDate(readList.get(0).getModDate())
        .delFlag(readList.get(0).isDelFlag())
        .fname(String.join(",", readList.stream().map(data -> data.getFname()).collect(Collectors.toList())))
        .view(readList.get(0).getView())
        .build();

        return dto;
        
    }

    @Override
    public Long registBoard(BoardDTO boardDTO) {
        log.info("service11111111111111111111111111111111111111111111111");
        // Board board = modelMapper.map(boardReadDTO, Board.class);
        Category dtoCategory = Category.builder().cateno(boardDTO.getCateno()).build();
        MemberAccount accountDTO = MemberAccount.builder().mno(boardDTO.getMno()).build();
        
        


        Board board = Board.builder()
        .title(boardDTO.getTitle())
        .content(boardDTO.getContent())
        .category(dtoCategory)
        .member(accountDTO)
        .view(boardDTO.getView())
        .build();

        boardDTO.getImages().forEach(img -> {
            log.info(img);
            board.addImage(img);
            log.info("end of product add");
        });

        log.info("Mapping data");
        boardRepository.save(board);
        boardDTO.setBno(board.getBno());
        return boardDTO.getBno();
    }

    

    @Override
    public Long deleteBoard(Long bno) {

        Optional<Board> result = boardRepository.findById(bno);

        Board board = result.orElseThrow();

        board.changeDelFlag(true);
        board.changeContent("삭제된 게시물 입니다.");
        board.changeTitle("삭제된 게시물 입니다.");

        boardRepository.save(board);

        return bno;

    }

    @Override
    public Long modifyBoard(BoardDTO boardDTO) {

        Optional<Board> result = boardRepository.findById(boardDTO.getBno());

        Board board = result.orElseThrow();

        board.changeContent(boardDTO.getContent());
        board.changeTitle(boardDTO.getTitle());
                // // 엔티티에 담긴 기존 이미지 이름 목록
                List<String> oldFileNames = board.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

                board.clearImages();
        
                // dto의 파일 이름을 엔티티에 담고 저장
                boardDTO.getImages().forEach(fname -> board.addImage(fname));
                boardRepository.save(board);
        
                // 세이브 성공했으면 기존 파일들 중 productDTO.getImages()에 없는 파일 찾기
                List<String> newFiles = boardDTO.getImages();
                List<String> wantDeleteFiles = oldFileNames.stream().filter(f -> newFiles.indexOf(f) == -1).collect(Collectors.toList());
        
                fileUploader.removeFiles(wantDeleteFiles);

        boardRepository.save(board);
        return boardDTO.getBno();
        
    }

    @Override
    public PageResponseDTO<BoardListDTO> getListSameWriter(Long mno, PageRequestDTO pageRequestDTO) {
        
        return boardRepository.searchSameWriter(mno, pageRequestDTO);
    }

    

}
