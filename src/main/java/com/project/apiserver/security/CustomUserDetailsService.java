package com.project.apiserver.security;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("-----------------------------loadUserByUsername--------------------------------");
        log.info("-----------------------------loadUserByUsername--------------------------------");
        log.info(username);
        log.info("-----------------------------loadUserByUsername--------------------------------");
        log.info("-----------------------------loadUserByUsername--------------------------------");
        
        MemberAccount memberAccount = memberRepository.getInfoEmail(username);
        log.info(memberAccount);

        if (memberAccount == null) {
            throw new UsernameNotFoundException("Not Found");
        }

        MemberAccountDTO memberDTO = MemberAccountDTO
        .builder()
        .mno(memberAccount.getMno())
        .email(memberAccount.getEmail())
        .pw(memberAccount.getPw())
        .nickname(memberAccount.getNickname())
        .roleName(memberAccount.getRoleName())
        .intro(memberAccount.getIntro())
        .social(memberAccount.isSocial())
        .delFlag(memberAccount.isDelFlag())
        .address(memberAccount.getAddress())
        .build();
     
            
         log.info(memberDTO);

        return memberDTO;
      

    }

}
