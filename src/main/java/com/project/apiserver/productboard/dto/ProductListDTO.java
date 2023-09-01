package com.project.apiserver.productboard.dto;

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
public class ProductListDTO {

    private Long pno;
    private boolean delFlag;
    private String pdesc;
    private String pname;
    private int price;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

    private Long mno;
    private String email;
    private String nickname;
    private String roleName;

    private Integer procateno;
    private String procatename;

    private String fname;
    private Integer view;

    // 등록, 수정 업로드 된 파일 데이터를 수집하는 용도
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();
    

}
