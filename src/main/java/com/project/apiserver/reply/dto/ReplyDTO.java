package com.project.apiserver.reply.dto;

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
public class ReplyDTO {
    
    private Long rno;

    private String reply;
    private Long gno;
    @Builder.Default
    private boolean ord = false;
    private boolean delFlag;
    
    private Long mno;
    
    private String email;
    private String nickname;
    private Long bno;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

}
