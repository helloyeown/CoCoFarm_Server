package com.project.apiserver.member.service;

import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.member.dto.MemberPageRequestDTO;
import com.project.apiserver.member.dto.MemberPageResponseDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface MemberService {
    
    MemberPageResponseDTO<MemberAccountDTO> getMemberList(MemberPageRequestDTO memberPageRequestDTO);

    MemberAccountDTO getOne(Long mno);

    Long registerMember(MemberAccountDTO accountDTO);

    void deleteMember(Long mno);

    void modifyMember(MemberAccountDTO memberAccountDTO);


    MemberAccountDTO getInfoByEmail(String email);
}


