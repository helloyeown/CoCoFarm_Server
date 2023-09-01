package com.project.apiserver.board.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable // FK 자동생성
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardImage {

  private String fname;
  private int ord;
  
}
