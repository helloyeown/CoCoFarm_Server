package com.project.apiserver.productboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.apiserver.productboard.dto.ProductReadDTO;
import com.project.apiserver.productboard.entity.Product;
import com.project.apiserver.productboard.repository.search.ProductSearch;

import jakarta.transaction.Transactional;



public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {
    
    // 상세보기용을 위한 JPQL 작업
    // @EntityGraph(attributePaths = "images")
    // @Query("select p from Product p where p.delFlag = false and p.pno = :pno ")
    // Product selectOne(@Param("pno")Long pno);

    // @Query("select new com.project.apiserver.productboard.dto.ProductReadDTO(p.pno, p.delFlag, p.pdesc, p.pname, p.price, p.modDate, m.mno, m.email, m.nickname, m.roleName, c.procateno, c.procatename, count(pi) as images) from Product p left join p.member m left join p.category c left join p.images pi where p.pno = :pno and p.delFlag=false")
    // ProductReadDTO selectOne(@Param("pno") Long pno);

    @EntityGraph(attributePaths = {"images"})
    @Query("select new com.project.apiserver.productboard.dto.ProductReadDTO(" +
           "p.pno, p.delFlag, p.pdesc, p.pname, p.price, p.modDate, " +
           "m.mno, m.email, m.nickname, m.roleName, " +
           "c.procateno, c.procatename, pi.fname, p.view, p.regDate) " +
           "from Product p " +
           "join p.member m " +
           "join p.category c " +
           "left outer join p.images pi " +
           "where p.delFlag = false and p.pno = :pno " +
           "order by p.pno desc")
    List<ProductReadDTO> selectOne(@Param("pno") Long pno);

    @Transactional
    @Modifying
    @Query("update Product p set p.view= p.view +1 where p.pno = :pno")
    int incrementView(@Param("pno") Long pno);

    // @EntityGraph(attributePaths = {"images"})
    // @Query("select new com.project.apiserver.productboard.dto.ProductListByMemberDTO(" +
    //         "p.pno, p.delFlag, p.pname, p.price, p.modDate, p.category.procateno, p.view, pi.fname) " +
    //         "from Product p join p.images pi " + 
    //         "where p.member.mno = :mno and pi.ord = 0")
    // List<ProductListByMemberDTO> getListByMno(@Param("mno") Long mno);

}