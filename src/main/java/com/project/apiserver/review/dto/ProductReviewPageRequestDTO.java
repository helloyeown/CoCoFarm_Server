package com.project.apiserver.review.dto;

import com.project.apiserver.common.PageRequestDTO;

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
// @EqualsAndHashCode(callSuper=false)
public class ProductReviewPageRequestDTO extends PageRequestDTO {
        
    private Long pno;
    
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size =20;

}
