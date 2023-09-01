package com.project.apiserver.review.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.apiserver.review.dto.ProductReviewDTO;
import com.project.apiserver.review.entity.ProductReview;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {

    @Query("select new com.project.apiserver.review.dto.ProductReviewDTO(r.rno, r.review, r.delFlag,  m.mno, m.email, m.nickname, r.product.pno, i.fname, r.regDate, r.modDate) from ProductReview r join r.member m left outer join r.images i on m.mno = r.member.mno where r.product.pno = :pno and r.delFlag = false and (i.ord = 0 or ord is null)")
    Page<ProductReviewDTO> getReviewList(@Param("pno") Long pno, Pageable pageable);

    @EntityGraph(attributePaths = {"images"})
    @Query("select new com.project.apiserver.review.dto.ProductReviewDTO(r.rno, r.review, r.delFlag,  m.mno, m.email, m.nickname, r.product.pno, i.fname, r.regDate, r.modDate) from ProductReview r join r.member m left outer join r.images i on m.mno = r.member.mno where r.rno = :rno")
    List<ProductReviewDTO> readReview(@Param("rno") Long rno);

}
