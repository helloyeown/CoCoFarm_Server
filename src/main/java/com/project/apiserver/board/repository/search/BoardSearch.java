package com.project.apiserver.board.repository.search;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.project.apiserver.board.dto.BoardListDTO;

import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;


public interface BoardSearch {

   PageResponseDTO<BoardListDTO> search(PageRequestDTO pageRequestDTO);

    // 같은 게시자의 글중에 검색을 하는기능
    PageResponseDTO<BoardListDTO> searchSameWriter(Long mno,PageRequestDTO pageRequestDTO);


    // Pageable을 반환하는 것을 만들어주는 메소드
    // Pageable을 계속쓰게되므로 반복해서 편히 쓰기위해서 default 접근제한자로 선언후 사용
    default Pageable makePageable(PageRequestDTO pageRequestDTO){

        Pageable pageable = 
            PageRequest.of(
                pageRequestDTO.getPage()-1, 
                pageRequestDTO.getSize(), 
                Sort.by("category.cateno").descending().and(Sort.by("bno").descending()));
        
        return pageable;
    } 
    
}
