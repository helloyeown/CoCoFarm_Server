package com.project.apiserver.member.controller;


import java.util.Map;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.member.dto.MemberPageRequestDTO;
import com.project.apiserver.member.dto.MemberPageResponseDTO;
import com.project.apiserver.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
@Log4j2
public class MemberContoller {


    private final MemberService memberService;


    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/farmer")
    public MemberPageResponseDTO<MemberAccountDTO> getFarmerList(@ParameterObject MemberPageRequestDTO memberPageRequestDTO){
        log.info("test");
        memberPageRequestDTO.setRoleName("FARMER"); 
        log.info("-----------------------------------------");
        return memberService.getMemberList(memberPageRequestDTO);

    }
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("admin/consumer")
    public MemberPageResponseDTO<MemberAccountDTO> getConsumerList(@ParameterObject MemberPageRequestDTO pageRequestDTO){

        pageRequestDTO.setRoleName("CONSUMER");
        
        return memberService.getMemberList(pageRequestDTO);
    }


    @PostMapping("member")
    public Map<String, Long> registerMember( MemberAccountDTO memberAccountDTO){
        log.info(memberAccountDTO);

        Long returnNum = memberService.registerMember(memberAccountDTO);

        return Map.of("result", returnNum);

    }

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONSUMER','ROLE_FARMER')")
    @GetMapping("admin/read/{mno}")
    public MemberAccountDTO getOne(@PathVariable Long mno){

         return memberService.getOne(mno);
    }    

    // @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONSUMER','ROLE_FARMER')")
    @DeleteMapping("admin/{mno}")
    public Map<String, String> deleteMember(@PathVariable Long mno) {

        
        memberService.deleteMember(mno);

        return Map.of("result","succeess"); 
    }

    // @PreAuthorize("hasAnyRole('ROLE_CONSUMER','ROLE_FARMER')")
    @PutMapping("member/modify")
    public Map<String, String> modifyMember( MemberAccountDTO memberAccountDTO){

        log.info("ModifyConsumer ------------------------------------------ ");
        log.info(memberAccountDTO);
        memberService.modifyMember(memberAccountDTO);

        return Map.of("result", "succeess");

    }
    
}
