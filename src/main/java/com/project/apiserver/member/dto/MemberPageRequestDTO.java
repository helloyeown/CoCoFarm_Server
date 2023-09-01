package com.project.apiserver.member.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberPageRequestDTO{
    
    @Builder.Default
    private int page =1;
    @Builder.Default
    private int size = 10;
    private String type;
    private String keyword;
    private String roleName;

    public MemberPageRequestDTO() {
        this(1,10);
    }

    public MemberPageRequestDTO(int page, int size) {
        this(page,size,null, null, null);
        
    }
    public MemberPageRequestDTO(int page, int size, String type, String keyword, String roleName){
    
        this.page = page <=0 ? 1: page;
        this.size = size <0 || size >= 100? 10 : size;

        this.type = type;
        this.keyword = keyword;
        this.roleName = roleName;
      
    }
}
