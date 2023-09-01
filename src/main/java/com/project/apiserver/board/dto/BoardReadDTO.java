package com.project.apiserver.board.dto;

import java.time.LocalDateTime;


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
public class BoardReadDTO {
    

    private Long bno;
    private String title;
    private String content;
    private String email;
    private String nickname;
    private String catename;
    private Integer cateno;

    private Long mno;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

    private boolean delFlag;

    private String fname; //bi.fname에 해당하는 필드 추가

    private Integer view;


    
}
