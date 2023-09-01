package com.project.apiserver.member.dto;

import lombok.Getter;

@Getter
public enum MemberAccountRole {
    
    FARMER("FARMER"), ADMIN("ADMIN"), CONSUMER("CONSUMER");

    private String roleName;

    MemberAccountRole(String roleName){
        this.roleName = roleName;
    }
}
