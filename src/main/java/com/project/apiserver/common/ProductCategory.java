package com.project.apiserver.common;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_product_category")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductCategory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer procateno; 
    private String procatename;

    public void changeCateno(Integer cateno){

        this.procateno = cateno;

    }

}
