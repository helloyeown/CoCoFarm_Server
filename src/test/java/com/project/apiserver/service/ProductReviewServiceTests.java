package com.project.apiserver.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.project.apiserver.review.dto.ProductReviewPageRequestDTO;
import com.project.apiserver.review.dto.ProductReviewReadDTO;
import com.project.apiserver.review.service.ProductReviewService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductReviewServiceTests {
    
    @Autowired
    private ProductReviewService service;

    // 목록
    @Test
    public void getListTest(){

        ProductReviewPageRequestDTO dto = ProductReviewPageRequestDTO.builder().pno(661L).build();

        log.info(service.getList(dto));

    }

    // 조회
    @Test
    public void readTest(){

        log.info(service.readProductReview(3L));

    }

    // 등록
    @Test
    public void registerTest(){

        List<String> images = new ArrayList<>();

        images.add("test.jpg");
        images.add("test2.jpg");

        ProductReviewReadDTO dto = ProductReviewReadDTO.builder()
            .review("insert Test")
            .mno(1L)
            .pno(636L)
            .images(images).build();

        service.register(dto);

    }

    // 삭제
    @Test
    @Transactional
    @Commit
    public void deleteTest(){

        service.delete(3L);

    }

    // 수정
    @Test
    @Transactional
    @Commit
    public void modifyTest(){

        List<String> images = new ArrayList<>();

        images.add("modify.jpg");
        images.add("modify2.jpg");

        ProductReviewReadDTO dto = ProductReviewReadDTO.builder()
            .rno(4L)
            .review("modfied")
            .images(images).build();

        service.modify(dto);

    }

}
