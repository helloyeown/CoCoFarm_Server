package com.project.apiserver.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.member.dto.MemberPageRequestDTO;
import com.project.apiserver.member.service.MemberService;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MemberServiceTests {
    
    @Autowired(required = false)
    MemberService service;

    @Autowired
    PasswordEncoder passwordEncoder;   

    @Test
    public void getMemberList(){

            MemberPageRequestDTO memberPageRequestDTO = MemberPageRequestDTO.builder()
            .build();

           log.info(service.getMemberList(memberPageRequestDTO));
    }

    @Test
    public void getMemberOne(){

        
           log.info(service.getOne(10L));
    }
    

    @Test
    public void modifyMember(){

        // MemberAccountDTO accountDTO = MemberAccountDTO.builder()
        // .mno(9L)
        // .intro("수정된 맴버")
        // .nickname("수정된 닉네임")
        // .pw("나중에 인코딩 해줄 비번")
        // .build();

        // service.modifyMember(accountDTO);

    }


    @Test
    public void deleteMember(){
        service.deleteMember(8L);
    }

    @Test
    public void registerMemberTest(){
        log.info("insert start");
        MemberAccountDTO dto = MemberAccountDTO.builder().email("aaa1223@email.com")
        .pw(passwordEncoder.encode("1111"))
        .nickname("serviceTestInesrt")
        .intro("서비스테스트용DTO소개")
        .roleName("FARMER")
        .build();
        log.info("set dto");
        service.registerMember(dto);
        log.info("success");

    }
}

