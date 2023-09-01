package com.project.apiserver.security.filter;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.util.JWTUtil;

import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    // true이면 filter 를 타지 않고, false면 filter를 타야만 된다.
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // Preflight 일때 제외시켜줘야된다.
        if(request.getMethod().equals("OPTIONS")) {
            return true;
        }


        String path = request.getRequestURI();
        if(path.startsWith("/")){
            return true;
        }
        if(path.endsWith(".ico")){
            return true;
        }

        // if(path.equals("/api/member/login")) {
        //     return true;
        // }
        // if(path.startsWith("/api/admin/")){
        //     return true;
        // }
        // //나중에 걸어야됨 삭제요망
        // if(path.startsWith("/api/board/")){
        //     return true;
        // }
        // //나중에 걸어야됨 삭제요망
        // if(path.startsWith("/api/replies/")){
        //     return true;
        // }


        return false;

    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        log.info("--------------doFilterInternal------------------");

        log.info("--------------doFilterInternal------------------");

        // 필터 통과후 다음 필터나 컨트롤러등을 호출 Security에서 잡아야된다
        String authHeaderStr = request.getHeader("Authorization");

        try {
            // Bearer accestoken...
            String accessToken = authHeaderStr.substring(7);
            Map<String, Object> claims = JWTUtil.validateToken(accessToken);

            log.info("JWT claims: " + claims);

            filterChain.doFilter(request, response);
            // clamis 안에 들어있는 정보들을 추출하는 과정
            Long mno = (Long) claims.get("mno");
            String email = (String) claims.get("email");
            String pw = (String) claims.get("pw");
            String nickname = (String) claims.get("nickname");
            String roleName = (String) claims.get("roleName");
            String intro = (String) claims.get("intro");
            Boolean social = (Boolean) claims.get("social");
            Boolean delFlag = (Boolean) claims.get("delFlag");


            // MemberAccountDTO 사용자 생성
            MemberAccountDTO memberAccountDTO = MemberAccountDTO
            .builder()
            .mno(mno)
            .email(email)
            .pw(pw)
            .nickname(nickname)
            .roleName(roleName)
            .intro(intro)
            .social(social)
            .delFlag(delFlag)
            .build();
            log.info("-----------------------------------");
            log.info(memberAccountDTO);
            log.info(memberAccountDTO.getAuthorities());
            // 만든 meberDTO를 security안에 포함시키는 과정 
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberAccountDTO,
                    pw, memberAccountDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception e) {

            log.error("JWT Check Error..............");
            log.error(e.getMessage());

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("error", "ERROR_ACCESS_TOKEN"));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();

        }

    }
}