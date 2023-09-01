package com.project.apiserver.member.repository.search;

import com.project.apiserver.member.dto.MemberAccountDTO;

import com.project.apiserver.member.dto.MemberPageRequestDTO;
import com.project.apiserver.member.dto.MemberPageResponseDTO;
import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.member.entity.QMemberAccount;

import java.util.List;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MemberSearchImpl extends QuerydslRepositorySupport implements MemberSearch {

    public MemberSearchImpl() {
        super(MemberAccount.class);
    }

    @Override
    public MemberPageResponseDTO<MemberAccountDTO> search(MemberPageRequestDTO requestDTO) {

        Pageable pageable = makePageable(requestDTO);

        QMemberAccount account = QMemberAccount.memberAccount;

        String keyword = requestDTO.getKeyword();
        String searchType = requestDTO.getType();
        String roleName = requestDTO.getRoleName();

        JPQLQuery<MemberAccount> searchQuery = from(account);
        searchQuery.where(account.delFlag.eq(Boolean.FALSE));
        if (roleName != null) {
            searchQuery.where(account.roleName.eq(roleName));
        }
        if (keyword != null && searchType != null) {

            BooleanBuilder searchBuilder = new BooleanBuilder();
            String[] searchArr = searchType.split("");

            for (String typeword : searchArr) {

                switch (typeword) {
                    case "e" -> searchBuilder.or(account.email.contains(keyword));
                    case "n" -> searchBuilder.or(account.nickname.contains(keyword));
                }

               
            }
             searchQuery.where(searchBuilder);
        }
        log.info("------------------before page----------------");
        this.getQuerydsl().applyPagination(pageable, searchQuery);
        log.info("-------------- after page--------------------");
        JPQLQuery<MemberAccountDTO> queryDto = searchQuery.select(Projections.bean(
            MemberAccountDTO.class,
            account.mno,
            account.email,
            account.pw,
            account.nickname,
            account.regDate,
            account.modDate,
            account.intro,
            account.roleName,
            account.profile
        ));

        List<MemberAccountDTO> accountDTOs = queryDto.fetch();

        long total = queryDto.fetchCount();

        //JPQLQuery<Tuple> tupleQuery = searchQuery.select(account.mno,account.email,account.pw,account.nickname,account.)

        return new MemberPageResponseDTO<>(accountDTOs, total, requestDTO);
     


    }
}
