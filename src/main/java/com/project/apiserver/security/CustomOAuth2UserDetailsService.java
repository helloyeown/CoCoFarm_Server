package com.project.apiserver.security;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.member.dto.MemberAccountRole;
import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserDetailsService extends DefaultOAuth2UserService {

  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

    log.info("userRequest....");
    log.info(userRequest);

    log.info("oauth2 user.....................................");

    ClientRegistration clientRegistration = userRequest.getClientRegistration();
    String clientName = clientRegistration.getClientName();

    log.info("NAME: " + clientName);
    OAuth2User oAuth2User = super.loadUser(userRequest);
    Map<String, Object> paramMap = oAuth2User.getAttributes();

    String email = null;

    switch (clientName) {
      case "kakao":
        email = getKakaoEmail(paramMap);
        break;
    }

    log.info("===============================");
    log.info(email);
    log.info("===============================");


    MemberAccountDTO memberAccountDTO = generateDTO(email, paramMap);

     log.info("MEMBERACCOUNTDTO: " + memberAccountDTO);

     return memberAccountDTO;
    
  }

  private MemberAccountDTO generateDTO(String email, Map<String, Object> params){
 
    MemberAccount memberAccount = memberRepository.getInfoEmail(email);

    //데이터베이스에 해당 이메일을 사용자가 없다면
    if(memberAccount == null){
        //회원 추가 -- mid는 이메일 주소/ 패스워드는 1111
        MemberAccount socialMember = memberAccount.builder()
                .email(email)
                .pw(passwordEncoder.encode("1111"))
                .nickname("social")
                .intro("Social login")
                .delFlag(false)
                .social(true)
                .build();
        socialMember.changeRole(MemberAccountRole.CONSUMER);
        memberRepository.save(socialMember);
        
        //MemberAccountDTO 구성 및 반환
        
        MemberAccountDTO memberAccountDTO = MemberAccountDTO
        .builder()
        .mno(socialMember.getMno())
        .email(email)
        .pw(socialMember.getPw())
        .nickname(socialMember.getNickname())
        .intro(socialMember.getIntro())
        .delFlag(socialMember.isDelFlag())
        .social(socialMember.isSocial())
        .roleName(socialMember.getRoleName().toString())
        .build();
        return memberAccountDTO;
    }else {

        MemberAccountDTO memberAccountDTO = MemberAccountDTO
        .builder()
        .mno(memberAccount.getMno())
        .email(email)
        .pw(memberAccount.getPw())
        .nickname(memberAccount.getNickname())
        .intro(memberAccount.getIntro())
        .delFlag(memberAccount.isDelFlag())
        .social(memberAccount.isSocial())
        .roleName(memberAccount.getRoleName().toString())
        .address(memberAccount.getAddress())
        .build();

        return memberAccountDTO;
    }
}

  private String getKakaoEmail(Map<String, Object> paramMap) {

    log.info("KAKAO-----------------------------------------");

    Object value = paramMap.get("kakao_account");

    log.info(value);

    LinkedHashMap accountMap = (LinkedHashMap) value;

    String email = (String) accountMap.get("email");

    log.info("email..." + email);

    return email;
  }
}
