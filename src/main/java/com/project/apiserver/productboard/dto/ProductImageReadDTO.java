package com.project.apiserver.productboard.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
public class ProductImageReadDTO {

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

    private String fname; // pi.fname에 해당하는 필드 추가

    private List<String> images = new ArrayList<>();
    
}
