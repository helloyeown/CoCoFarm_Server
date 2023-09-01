package com.project.apiserver.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.member.repository.MemberRepository;
import com.project.apiserver.productboard.entity.Product;
import com.project.apiserver.productboard.repository.ProductRepository;
import com.project.apiserver.review.entity.ProductReview;
import com.project.apiserver.review.repository.ProductReviewRepository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ProductReviewRepositoryTests {
    
    @Autowired
    private ProductReviewRepository repository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    // 등록
    @Test
    public void registerTest(){

        Optional<MemberAccount> result = memberRepository.findById(1L);
        MemberAccount member = result.orElseThrow();
        
        Optional<Product> result2 = productRepository.findById(636L);
        Product product = result2.orElseThrow();

        // List<ProductImage> list = new ArrayList<>();

        // ProductImage image1 = ProductImage.builder().fname("test.jpg").build();
        // ProductImage image2 = ProductImage.builder().fname("test2.jpg").build();
        
        // list.add(image1);
        // list.add(image2);

        for(int i=0; i<100; i++){

            ProductReview review = ProductReview.builder()
                .review("review test" + i)
                .member(member)
                .product(product).build();

            review.addImage("test.jpg");
            review.addImage("test2.jpg");

            repository.save(review);

        }

    }

    // 목록
    @Test
    @Transactional
    public void listTest(){

        // Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        // Page<ProductReview> result = repository.findAll(pageable);

        // log.info(result);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("rno").descending());

        log.info(repository.getReviewList(636L, pageable));

    }

    // 조회
    @Test
    @Transactional
    public void readTest(){

        // Optional<ProductReview> result = repository.findById(3L);

        // ProductReview review = result.orElseThrow();

        // log.info(review);

        log.info(repository.readReview(3L));

    }

    // 삭제
    @Test
    @Transactional
    @Commit
    public void deleteTest(){

        Optional<ProductReview> result = repository.findById(2L);

        ProductReview review = result.orElseThrow();

        review.changeDelFlag(true);
        review.changeReply("delete");
        review.clearImages();

        repository.save(review);

    }

    // 수정
    @Test
    @Transactional
    @Commit
    public void modifyTest(){

        Optional<ProductReview> result = repository.findById(3L);

        ProductReview review = result.orElseThrow();

        review.changeReply("modify");
        review.clearImages();
        review.addImage("modify.jpg");
        review.addImage("modify2.jpg");

        repository.save(review);

    }

    // 이미지
    @Test
    @Transactional
    @Commit
    public void imageTest(){

        // List<ProductReviewDTO> result = repository.readReview(207L);

        // log.info(result);

    }

}
