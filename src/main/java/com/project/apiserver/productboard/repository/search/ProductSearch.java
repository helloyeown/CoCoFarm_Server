package com.project.apiserver.productboard.repository.search;


import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.productboard.dto.ProductListByMemberDTO;
import com.project.apiserver.productboard.dto.ProductListDTO;

public interface ProductSearch {

    PageResponseDTO<ProductListDTO> search(PageRequestDTO requestDTO);

    PageResponseDTO<ProductListByMemberDTO> searchWithMno(PageRequestDTO requestDTO, Long mno);

    default Pageable makePageable(PageRequestDTO requestDTO){

        Pageable pageable = PageRequest.of(
            requestDTO.getPage() -1, requestDTO.getSize(), Sort.by("pno").descending());

        return pageable;
    }

}
