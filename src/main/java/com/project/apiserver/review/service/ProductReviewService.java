package com.project.apiserver.review.service;

import com.project.apiserver.review.dto.ProductReviewReadDTO;
import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.review.dto.ProductReviewDTO;
import com.project.apiserver.review.dto.ProductReviewPageRequestDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface ProductReviewService {

    PageResponseDTO<ProductReviewDTO> getList(ProductReviewPageRequestDTO dto);

    ProductReviewDTO readProductReview(Long rno);
    
    void register(ProductReviewReadDTO dto);

    void delete(Long rno);

    void modify(ProductReviewReadDTO dto);

}
