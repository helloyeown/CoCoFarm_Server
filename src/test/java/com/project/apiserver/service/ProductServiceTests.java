package com.project.apiserver.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.productboard.dto.ProductDTO;
import com.project.apiserver.productboard.service.ProductService;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductServiceTests {
    
    @Autowired
    private ProductService productService;

    @Test
    @Transactional
    public void getListTest(){

        PageRequestDTO pageRequestDTO = new PageRequestDTO();

         

         log.info(productService.getList(pageRequestDTO));

    }

    @Test
    @Transactional
    public void getListWithSearchTest(){

        PageRequestDTO dto = new PageRequestDTO();

        productService.getList(dto);

    }

    @Test
    @Transactional
    public void readTest(){

        productService.readOne(2L);

    }

    @Test
    public void registerTest(){

        List<String> images = new ArrayList<>();

        images.add(UUID.randomUUID().toString() + "_aaa.jpg");
        images.add(UUID.randomUUID().toString() + "_bbb.jpg");
        images.add(UUID.randomUUID().toString() + "_ccc.jpg");

        ProductDTO dto = ProductDTO.builder()
            .pname("test name1")
            .pdesc("test desc1")
            .price(5000)
            .mno(8L)
            .procateno(5)
            .images(images)
            .build();
            
        log.info("images");
        log.info(dto.getImages());

        productService.register(dto);

    }

    // 삭제
    @Test
    @Transactional
    @Commit
    public void deleteTest(){

        productService.delete(10L);

    }

    // 수정
    @Test
    @Transactional
    @Commit
    public void modifyTest(){

        List<String> list = new ArrayList<>();
        list.add("file.jpg");
        list.add("file2.jpg");

        ProductDTO dto = ProductDTO.builder()
            .pno(6L)
            .pdesc("수정테스트")
            .pname("사과")
            .price(18000)
            .procateno(2)
            .images(list).build();

        productService.modify(dto);

    }

    // 멤버별 리스트 조회
    @Test
    public void getListByMno(){

        PageRequestDTO dto = new PageRequestDTO(0, 10);

        log.info(productService.getListByMno(dto, 3L));

    }

}
