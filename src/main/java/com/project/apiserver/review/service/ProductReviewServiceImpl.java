package com.project.apiserver.review.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.member.repository.MemberRepository;
import com.project.apiserver.productboard.entity.Product;
import com.project.apiserver.productboard.repository.ProductRepository;
import com.project.apiserver.review.dto.ProductReviewReadDTO;
import com.project.apiserver.review.dto.ProductReviewDTO;
import com.project.apiserver.review.dto.ProductReviewPageRequestDTO;
import com.project.apiserver.review.entity.ProductReview;
import com.project.apiserver.review.repository.ProductReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewRepository repository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<ProductReviewDTO> getList(ProductReviewPageRequestDTO dto) {

        int pageNum = dto.getPage();

        Pageable pageable = PageRequest.of(pageNum - 1, 10, Sort.by("rno").descending());

        Page<ProductReviewDTO> result = repository.getReviewList(dto.getPno(), pageable);
        long totalCount = result.getTotalElements();

        List<ProductReviewDTO> dtoList = result.stream().map(review -> modelMapper.map(review, ProductReviewDTO.class)).collect(Collectors.toList());

        PageResponseDTO<ProductReviewDTO> responseDTO = new PageResponseDTO<>(dtoList, totalCount, dto);
        responseDTO.setPage(pageNum);

        return responseDTO;

    }

    @Override
    public ProductReviewDTO readProductReview(Long rno) {

        List<ProductReviewDTO> list = repository.readReview(rno);

        ProductReviewDTO dto = ProductReviewDTO.builder()
            .rno(list.get(0).getRno())
            .review(list.get(0).getReview())
            .delFlag(list.get(0).isDelFlag())
            .mno(list.get(0).getMno())
            .email(list.get(0).getEmail())
            .nickname(list.get(0).getNickname())
            .pno(list.get(0).getPno())
            .fname(String.join(",", list.stream().map(data -> data.getFname()).collect(Collectors.toList())))
            .regDate(list.get(0).getRegDate())
            .modDate(list.get(0).getModDate())
            .build();

        return dto;

    }

    @Override
    public void register(ProductReviewReadDTO dto) {

        Optional<Product> result = productRepository.findById(dto.getPno());
        Product product = result.orElseThrow();

        Optional<MemberAccount> result2 = memberRepository.findById(dto.getMno());
        MemberAccount member = result2.orElseThrow();

        // Product product = Product.builder().pno(dto.getPno()).build();
        // MemberAccount member = MemberAccount.builder().mno(dto.getMno()).build();

        ProductReview review = ProductReview.builder()
            .review(dto.getReview())
            .member(member)
            .product(product).build();

        dto.getImages().forEach(img -> {
            review.addImage(img);
        });

        repository.save(review);

    }

    @Override
    public void delete(Long rno) {

        Optional<ProductReview> result = repository.findById(rno);
        ProductReview review = result.orElseThrow();

        review.changeDelFlag(true);
        review.changeReply("삭제된 리뷰입니다.");
        review.clearImages();

        repository.save(review);

    }

    @Override
    public void modify(ProductReviewReadDTO dto) {

        Optional<ProductReview> result = repository.findById(dto.getRno());
        ProductReview review = result.orElseThrow();

        review.changeReply(dto.getReview());
        review.clearImages();
        
        dto.getImages().forEach(img -> {
            review.addImage(img);
        });

        repository.save(review);

    }
    
}
