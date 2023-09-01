package com.project.apiserver.board.dto;

import java.time.LocalDateTime;

public interface IBoardListDTO {

    public Long getBno() ;

    public String getTitle();


    public String getEmail() ;


    public String getNickname();

    public String getCatename() ;

    public Long getRcnt() ;

    public Integer getCateno();

    public LocalDateTime getRegDate();



}
