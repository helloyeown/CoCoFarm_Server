package com.project.apiserver.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.ProductCategory;
import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.productboard.dto.ProductReadDTO;
import com.project.apiserver.productboard.entity.Product;
import com.project.apiserver.productboard.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;



    // 등록
    @Test
    public void insertTest(){

        MemberAccount account = MemberAccount.builder().mno(3L).build();
        
        for(int i=0; i<100; i++){

        for(int j= 1; j<=6; j++){
            
            ProductCategory category = ProductCategory.builder().procateno(j).build();

            Product product = Product.builder()
                            .pname("productTest")
                            .pdesc("descTest")
                            .member(account)
                            .price(35000)
                            .category(category)
                            .build();
            
            product.addImage(UUID.randomUUID().toString()+ "_aaa.jpg");
            product.addImage(UUID.randomUUID().toString()+"_bbb.jpg");
            product.addImage(UUID.randomUUID().toString()+"_ccc.jpg");

            repository.save(product);
        }
        }
    }

    // 목록
    @Test
    @Transactional
    public void getListTest(){

        List<Product> list = repository.findAll();

        list.forEach(data ->
        log.info(data + data.getCategory().getProcatename()));

    }

    @Test
    @Transactional
    public void getListTestWithSearch(){

        PageRequestDTO dto = new PageRequestDTO();

        log.info("------------------------------------------------------");
        log.info(repository.search(dto));
    }   

    // 조회
    @Test
    @Transactional
    public void readTest(){
        
        List<ProductReadDTO> list = repository.selectOne(2L);

        list.forEach(data -> log.info(data));

        // log.info(repository.selectOne(2L));

    }

    // 삭제
    @Test
    @Transactional
    @Commit
    public void deleteTest(){

        Product product = Product.builder().pno(5L).delFlag(true).pname("삭제된 게시물입니다.").pdesc("삭제된 게시물입니다.").build();

        repository.save(product);

    }

    // 수정
    @Test
    @Transactional
    @Commit
    public void modifyTest(){

        Optional<Product> result = repository.findById(6L);
        Product product = result.orElseThrow();

        product.changePdesc("수정테스트");
        product.changePname("modify");
        product.changePrice(80000);
        product.changeProductCategory(ProductCategory.builder().procateno(5).build());
        product.clearImages();
        product.addImage("abc.jpg");
        product.addImage("efg.jpg");

        repository.save(product);

    }

    // 멤버별 조회
    @Test
    public void selectByMno() {

        PageRequestDTO dto = new PageRequestDTO(0, 10, null, null, null);

        repository.searchWithMno(dto, 3L);

    }

}
