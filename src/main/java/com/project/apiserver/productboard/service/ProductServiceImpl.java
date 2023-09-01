package com.project.apiserver.productboard.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.project.apiserver.common.FileUploader;
import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.common.ProductCategory;
import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.productboard.dto.ProductDTO;
import com.project.apiserver.productboard.dto.ProductListByMemberDTO;
import com.project.apiserver.productboard.dto.ProductListDTO;
import com.project.apiserver.productboard.dto.ProductReadDTO;
import com.project.apiserver.productboard.entity.Product;
import com.project.apiserver.productboard.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {
    
    private final ProductRepository repository;
    private final FileUploader fileUploader;

    // 목록
    @Override
    public PageResponseDTO<ProductListDTO> getList(PageRequestDTO requestDTO) {

        // List<Product> result = repository.findAll();
        // log.info("result------------------------ " + result);

        PageResponseDTO<ProductListDTO> result = repository.search(requestDTO);

        // List<ProductDTO> dtoList = result.stream().map(e -> modelMapper.map(e, ProductDTO.class)).collect(Collectors.toList());
        // log.info("dtoList------------------------ " + dtoList);
        
        return result;

    }

    // 조회
    @Override
    public ProductReadDTO readOne(Long pno) {
        log.info("--------------------");
        repository.incrementView(pno);
        log.info("1234444444");
        List<ProductReadDTO> list = repository.selectOne(pno);
        
        ProductReadDTO dto = ProductReadDTO
        .builder()
        .pno(list.get(0).getPno())
        .delFlag(list.get(0).isDelFlag())
        .pdesc(list.get(0).getPdesc())
        .pname(list.get(0).getPname())
        .price(list.get(0).getPrice())
        .modDate(list.get(0).getModDate())
        .mno(list.get(0).getMno())
        .fname(String.join(",", list.stream().map(data -> data.getFname()).collect(Collectors.toList())))
        .email(list.get(0).getEmail())
        .nickname(list.get(0).getNickname())
        .roleName(list.get(0).getRoleName())
        .procatename(list.get(0).getProcatename())
        .procateno(list.get(0).getProcateno())
        .view(list.get(0).getView())
        .regDate(list.get(0).getRegDate())
        .build();

        log.info("-------------------------------");
        log.info(dto);

        return dto;

    }

    // 등록
    @Override
    public void register(ProductDTO dto) {

        MemberAccount member = MemberAccount.builder().mno(dto.getMno()).build();
        ProductCategory category = ProductCategory.builder().procateno(dto.getProcateno()).build();

        Product product = Product.builder()
            .pname(dto.getPname())
            .pdesc(dto.getPdesc())
            .price(dto.getPrice())
            .member(member)
            .view(dto.getView())
            .category(category).build();

        dto.getImages().forEach(img -> {
            log.info(img);
            product.addImage(img);
            log.info("end of product add");
        });

        repository.save(product);

    }

    // 삭제
    @Override
    public void delete(Long pno) {

        Optional<Product> result = repository.findById(pno);
        Product product = result.orElseThrow();

        product.changeDel(true);
        product.changePname("삭제된 게시물입니다.");
        product.changePdesc("삭제된 게시물입니다.");
        product.changePrice(0);
        product.clearImages();
        
        repository.save(product);

        List<String> fileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());
        fileUploader.removeFiles(fileNames);

    }

    @Override
    public void modify(ProductDTO productDTO) {
        
        Optional<Product> result = repository.findById(productDTO.getPno());
        Product product = result.orElseThrow();

        product.changePdesc(productDTO.getPdesc());
        product.changePname(productDTO.getPname());
        product.changePrice(productDTO.getPrice());
        product.changeProductCategory(ProductCategory.builder().procateno(productDTO.getProcateno()).build());
        
        // // 엔티티에 담긴 기존 이미지 이름 목록
        List<String> oldFileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

        product.clearImages();

        // dto의 파일 이름을 엔티티에 담고 저장
        productDTO.getImages().forEach(fname -> product.addImage(fname));
        repository.save(product);

        // 세이브 성공했으면 기존 파일들 중 productDTO.getImages()에 없는 파일 찾기
        List<String> newFiles = productDTO.getImages();
        List<String> wantDeleteFiles = oldFileNames.stream().filter(f -> newFiles.indexOf(f) == -1).collect(Collectors.toList());

        fileUploader.removeFiles(wantDeleteFiles);

    }

    @Override
    public PageResponseDTO<ProductListByMemberDTO> getListByMno(PageRequestDTO requestDTO, Long mno) {

        return repository.searchWithMno(requestDTO, mno);

    }
    

}
