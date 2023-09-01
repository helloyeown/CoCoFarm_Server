package com.project.apiserver.review.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ProductReviewDTO {

    private Long rno;
    private String review;
    private boolean delFlag;

    private Long mno;
    private String email;
    private String nickname;

    private Long pno;

    private String fname;

    // @Builder.Defaul
    
    // private List<String> images = new ArrayList<>();
    
    // // 등록, 수정 업로드 된 파일 데이터를 수집하는 용도
    // @Builder.Default
    // private List<MultipartFile> files = new ArrayList<>();
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

}
