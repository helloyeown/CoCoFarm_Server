package com.project.apiserver.member.repository;

import com.project.apiserver.member.entity.MemberAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.apiserver.member.repository.search.MemberSearch;

public interface MemberRepository extends JpaRepository<MemberAccount, Long>, MemberSearch{

    @Query("select m from MemberAccount m where m.mno= :mno and m.delFlag=false")
    MemberAccount getOne(@Param("mno")Long mno);

    @Query("select m from MemberAccount m where m.email = :email")
    MemberAccount getInfoEmail(@Param("email") String email);
    
}
