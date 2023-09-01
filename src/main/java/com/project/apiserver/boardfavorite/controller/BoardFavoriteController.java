package com.project.apiserver.boardfavorite.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.apiserver.boardfavorite.service.BoardFavoriteService;
import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boardfavorite/")
@RequiredArgsConstructor
public class BoardFavoriteController {

     private final MemberService memberService;
     private final BoardFavoriteService bFavoriteService;

    @PostMapping("{bno}")
    public Map<String, String> addFavorite(MemberAccountDTO account,
            @PathVariable("bno") Long bno) {

        MemberAccountDTO dto = memberService.getInfoByEmail(account.getEmail());

        bFavoriteService.incrementFavorite(bno,dto.getMno());

        return Map.of("result", "Success");

    }

    @DeleteMapping("{bno}/{email}")
    public Map<String, String> deleteFavorite(@PathVariable("email") String account,
            @PathVariable("bno") Long bno) {

        MemberAccountDTO dto = memberService.getInfoByEmail(account);

        bFavoriteService.deleteFavorite(bno,dto.getMno());

        return Map.of("result", "Success");

    }
    @GetMapping("{bno}")
    public Long countFavorite(@PathVariable("bno") Long bno){

        return bFavoriteService.countFavorite(bno);
    }
    @GetMapping("{bno}/check")
    public Long checkFavorite(MemberAccountDTO account,
            @PathVariable("bno") Long bno) {

        MemberAccountDTO dto = memberService.getInfoByEmail(account.getEmail());

        return bFavoriteService.checkFavorite(bno, dto.getMno());
    }
    
}
