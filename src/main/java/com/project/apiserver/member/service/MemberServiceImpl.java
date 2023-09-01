package com.project.apiserver.member.service;

import java.util.Optional;

import com.project.apiserver.member.entity.MemberAccount;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.apiserver.common.FileUploader;
import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.member.dto.MemberPageRequestDTO;
import com.project.apiserver.member.dto.MemberPageResponseDTO;
import com.project.apiserver.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository repository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final FileUploader fileUploader;
    // Read table one row
    @Override
    public MemberAccountDTO getOne(Long mno) {

        Optional<MemberAccount> result = repository.findById(mno);

        MemberAccount member = result.orElseThrow();
        log.info("-==========================");
        log.info(member);
        log.info("-==========================");
        // Member(mno=50, email=aaa49@email.com, pw=1111, nickname=nickname49, intro=차은우 짱50)
        MemberAccountDTO dto = modelMapper.map(member, MemberAccountDTO.class);

        return dto;

    }


    // read table list with search and pagination
    @Override
    public MemberPageResponseDTO<MemberAccountDTO> getMemberList(MemberPageRequestDTO memberPageRequestDTO) {

        log.info("----------------------2");

        return repository.search(memberPageRequestDTO);

    }



    @Override
    public void deleteMember(Long mno) {

        Optional<MemberAccount> result = repository.findById(mno);

        MemberAccount member =  result.orElseThrow();
        
        member.delete();
        fileUploader.removeProfile(member.getProfile());
        repository.save(member);
    }

    @Override
    public void modifyMember(MemberAccountDTO memberAccountDTO) {

        log.info("mno------------------------------");
        log.info(memberAccountDTO);

        Optional<MemberAccount> result =repository.findById(memberAccountDTO.getMno());

        log.info("modify service1.......................");

        MemberAccount member =  result.orElseThrow();
        String oldProfile = member.getProfile();
        fileUploader.removeProfile(oldProfile);
        log.info("modify service.2......................");
        fileUploader.uploadProfile(memberAccountDTO.getFile());

        member.changeNickname(memberAccountDTO.getNickname());
        member.changePw(passwordEncoder.encode(memberAccountDTO.getPw()));
        member.changeIntro(memberAccountDTO.getIntro());
        member.changeAddress(memberAccountDTO.getAddress());
        member.changeProfile(fileUploader.uploadProfile(memberAccountDTO.getFile())!=null ?fileUploader.uploadProfile(memberAccountDTO.getFile()):"");
        member.changeSocialFalse();

        log.info("modify service3 --------------");
        repository.save(member);
        
    }


    @Override
    public Long registerMember(MemberAccountDTO accountDTO) {

        String profile =fileUploader.uploadProfile(accountDTO.getFile());

        MemberAccount account = MemberAccount.builder()
        .email(accountDTO.getEmail())
        .pw(passwordEncoder.encode(accountDTO.getPw()))
        .nickname(accountDTO.getNickname())
        .intro(accountDTO.getIntro())
        .roleName(accountDTO.getRoleName())
        .address(accountDTO.getAddress())
        .profile(profile !=null ? profile :"")
        .build();
        
   
        return repository.save(account).getMno();

    }


    @Override
    public MemberAccountDTO getInfoByEmail(String email) {
        MemberAccount entity = repository.getInfoEmail(email);

        return modelMapper.map(entity, MemberAccountDTO.class);
    }

   
}
