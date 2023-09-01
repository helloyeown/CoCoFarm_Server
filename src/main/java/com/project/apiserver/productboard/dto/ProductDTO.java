package com.project.apiserver.productboard.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    
    private Long pno;
    private String pname;
    private String pdesc;
    private int price;
    private boolean delFlag;

    private Long mno;
    private Integer procateno;
    private Integer view;

    @Builder.Default
    private List<String> images = new ArrayList<>();

    // 등록, 수정 업로드 된 파일 데이터를 수집하는 용도
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

}
