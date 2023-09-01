package com.project.apiserver.subscription.service;


import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.apiserver.common.PageRequestDTO;
import com.project.apiserver.common.PageResponseDTO;
import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.member.entity.MemberAccount;
import com.project.apiserver.subscription.repository.SubscriptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subRepository;
    private final ModelMapper modelMapper;

    @Override
    public void incrementSub(Long fromUser, Long toUser) {

        subRepository.insertSubscriptionNative(fromUser, toUser);
    }

    @Override
    public void deleteSub(Long fromUser, Long toUser) {

        subRepository.deleteSubscriptionNative(fromUser, toUser);
    }

    @Override
    public Long countSub(Long toUser) {
        
        
       return subRepository.countSub(toUser);
    }

    @Override
    public PageResponseDTO<MemberAccountDTO> getListfrom(Long mno, PageRequestDTO pageRequestDTO) {
        
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize());

        Page<MemberAccount> result = subRepository.getListSubfrom(mno, pageable);
        long totalCount = result.getTotalElements();
        List<MemberAccountDTO> dtoList = result.get()
        .map(entitiy-> modelMapper.map(entitiy, MemberAccountDTO.class))
        .collect(Collectors.toList());


        return new PageResponseDTO<>(dtoList, totalCount, pageRequestDTO);
    }

    @Override
    public Long checkSub(Long fromUser, Long toUser) {
       
        return subRepository.checkSub(fromUser, toUser);
    }
    
}
