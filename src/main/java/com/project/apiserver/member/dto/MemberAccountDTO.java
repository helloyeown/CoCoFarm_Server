package com.project.apiserver.member.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberAccountDTO  implements UserDetails, OAuth2User {

    private Long mno;
    private String email;
    private String pw;
    private String nickname;
    private String roleName;
    private String intro;
    private boolean delFlag;
    private boolean social;
    private String address;
    private String profile;
    private MultipartFile file;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modDate;

    public Map<String, Object> getClaims() {

        // JWT 만들때 필요한 정보들만 넣어 놓는 메소드
        Map<String, Object> map = new HashMap<>();

        map.put("mno", mno);
        map.put("email", email);
        map.put("pw", pw);
        map.put("nickname", nickname);
        map.put("roleName", roleName);

        map.put("intro", intro);
        map.put("delFlag", delFlag);
        map.put("social", social);
        map.put("address", address);
        map.put("profile",profile);
        return map;

    }

    @Override
    public Map<String, Object> getAttributes() {

        return null;

    }

    @Override
    public String getName() {

        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + roleName);
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return this.pw;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.delFlag;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
   

}
