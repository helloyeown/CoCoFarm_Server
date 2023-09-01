package com.project.apiserver.security.handler;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.util.JWTUtil;
 
import com.google.gson.Gson;
 
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
 
@Log4j2
public class OAuthAPILoginSuccessHandler implements AuthenticationSuccessHandler{
 
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
   
   
    log.info("OAuth onAuthenticationSuccess.................");
 
   
    String email = authentication.getName();
 
    log.info("SEND TO APPLICATION: " + email);
 
    MemberAccountDTO memberAccountDTO = (MemberAccountDTO) authentication.getPrincipal();
 
    Map<String, Object> claims = memberAccountDTO.getClaims();
 
    String accessToken = JWTUtil.generateToken(claims, 10);
 
    String refreshToken = JWTUtil.generateToken(claims, 60 * 24);
 
    Gson gson = new Gson();
 
 

    claims.put("accessToken", accessToken);
    claims.put("refreshToken", refreshToken);
 
    String jsonStr = gson.toJson(claims);
    // URL에 적용가능한 무자열로 변환 redirect는 get방식만 되기떄문에
    String encodeStr = URLEncoder.encode(jsonStr, "UTF-8");
      
    response.sendRedirect("http://localhost:3000/member/loginhandler?data="+encodeStr);
 
  }
 
}


