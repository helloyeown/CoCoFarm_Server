package com.project.apiserver.review.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.apiserver.common.FileUploader;
import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.review.dto.ProductReviewDTO;
import com.project.apiserver.review.dto.ProductReviewPageRequestDTO;
import com.project.apiserver.review.dto.ProductReviewReadDTO;
import com.project.apiserver.review.service.ProductReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
@Log4j2
@CrossOrigin
public class ProductReviewController {

    private final ProductReviewService service;
    private final FileUploader fileUploader;

    // 목록
    @GetMapping("/{pno}/list")
    public PageResponseDTO<ProductReviewDTO> getList(@PathVariable Long pno, ProductReviewPageRequestDTO dto){

        log.info("list controllerrrrrrrrrrrrrrrrrrrrrrrrrrrr");

        PageResponseDTO<ProductReviewDTO> result = service.getList(dto);
        log.info(result);

        return result;

    }

    // 조회
    @GetMapping("/{rno}")
    public ProductReviewDTO readOne(@PathVariable Long rno){

        return service.readProductReview(rno);

    }

    // 등록
    @PostMapping("")
    public Map<String, String> register(ProductReviewReadDTO dto){

        log.info("dto...................................");
        log.info(dto);

        List<String> fileNames = fileUploader.uploadFiles(dto.getFiles(), true);
        dto.setImages(fileNames);

        service.register(dto);

        return Map.of("result", "success");

    }

    // 삭제
    @DeleteMapping("/{rno}")
    public Map<String, Long> delete(@PathVariable Long rno){

        service.delete(rno);

        return Map.of("result", rno);

    }

    // 수정
    @PutMapping("")
    public Map<String, Long> modify(ProductReviewReadDTO dto){

        if(dto.getFiles() != null && dto.getFiles().size() > 0) {
            List<String> fileNames = fileUploader.uploadFiles(dto.getFiles(), true);
            dto.setImages(fileNames);
        }

        service.modify(dto);

        return Map.of("result", dto.getRno());

    }
    
}
