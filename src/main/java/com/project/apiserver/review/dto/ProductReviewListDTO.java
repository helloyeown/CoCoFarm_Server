package com.project.apiserver.review.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class ProductReviewListDTO {
    
    private Long rno;
    private String review;
    private boolean delFlag;

    private Long mno;
    private String email;
    private String nickname;

    private Long pno;

    private String fname;
    
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

}
