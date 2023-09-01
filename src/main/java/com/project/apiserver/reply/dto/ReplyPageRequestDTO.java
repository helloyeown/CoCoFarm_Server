package com.project.apiserver.reply.dto;

import com.project.apiserver.common.PageRequestDTO;

import lombok.EqualsAndHashCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ToString
@EqualsAndHashCode(callSuper=false)
public class ReplyPageRequestDTO extends PageRequestDTO {
    
    private Long bno;
    
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size =20;

    private boolean last;

}
