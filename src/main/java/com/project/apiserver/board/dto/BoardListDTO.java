package com.project.apiserver.board.dto;

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
public class BoardListDTO {

    private Long bno;
    private String title;
    private String email;
    private String content;
    private String nickname;
    private String catename;
    private Integer cateno;
    private Long rcnt;

    private Long mno;
    private Integer view;
    private String fname;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @Builder.Default
    private List<String> images = new ArrayList<>();

    // 등록, 수정 업로드 된 파일 데이터를 수집하는 용도
    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();
}    