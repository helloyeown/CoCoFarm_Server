package com.project.apiserver.productboard.service;



import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.productboard.dto.ProductDTO;
import com.project.apiserver.productboard.dto.ProductListByMemberDTO;
import com.project.apiserver.productboard.dto.ProductListDTO;
import com.project.apiserver.productboard.dto.ProductReadDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface ProductService {
    
    PageResponseDTO<ProductListDTO> getList(PageRequestDTO requestDTO);

    ProductReadDTO readOne(Long pno);

    void register(ProductDTO productDTO);

    void delete(Long pno);

    void modify(ProductDTO productDTO);

    PageResponseDTO<ProductListByMemberDTO> getListByMno(PageRequestDTO requestDTO, Long mno);

}
