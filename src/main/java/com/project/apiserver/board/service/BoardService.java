package com.project.apiserver.board.service;



import com.project.apiserver.board.dto.BoardDTO;
import com.project.apiserver.board.dto.BoardListDTO;
import com.project.apiserver.board.dto.BoardReadDTO;
import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface BoardService {

   PageResponseDTO<BoardListDTO> getList(PageRequestDTO pageRequestDTO);

  BoardReadDTO getOne(Long bno);

   Long registBoard(BoardDTO boardDTO);

   //삭제
   Long deleteBoard(Long bno);

   //수정
   Long modifyBoard(BoardDTO boardDTO);

   PageResponseDTO<BoardListDTO> getListSameWriter(Long mno,PageRequestDTO pageRequestDTO);
   

}
