package com.project.apiserver.subscription.service;


import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.member.dto.MemberAccountDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface SubscriptionService {
    
    void incrementSub(Long fromUser, Long toUser);
    void deleteSub(Long fromUser, Long toUser);
    Long countSub(Long toUser);
    PageResponseDTO<MemberAccountDTO> getListfrom(Long mno, PageRequestDTO pageRequestDTO);
    Long checkSub(Long fromUser, Long toUser);
}
