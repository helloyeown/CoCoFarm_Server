package com.project.apiserver.common;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PageRequestDTO {

    // 가장 기본적이므로 Default 값필요

    private int page = 1;
    private int size = 10;

    private String type, keyword;
    
    private Integer cateno;

    public PageRequestDTO() {
        this(1,10);
    }

    public PageRequestDTO(int page, int size) {
        this(page,size,null, null, null);
        
    }
    public PageRequestDTO(int page, int size, String type, String keyword){
    
        this.page = page <=0 ? 1: page;
        this.size = size <0 || size >= 100? 10 : size;

        this.type = type;
        this.keyword = keyword;
        this.cateno = null;
    }
    public PageRequestDTO(int page, int size, String type, String keyword, Integer cateno){
    
        this.page = page <=0 ? 1: page;
        this.size = size <0 || size >= 100? 10 : size;

        this.type = type;
        this.keyword = keyword;
        this.cateno = cateno;
    }
    public void setCategory(Integer cateno){
       
        this.cateno = cateno;
    }


}