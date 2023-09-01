package com.project.apiserver.productfavorite.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.apiserver.member.dto.MemberAccountDTO;
import com.project.apiserver.member.service.MemberService;
import com.project.apiserver.productfavorite.service.ProductFavoriteService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/productfavorite/")
@RequiredArgsConstructor
@Log4j2
public class ProductFavoriteController {

    private final MemberService memberService;
    private final ProductFavoriteService pFavoriteService;

    @PostMapping("{pno}")
    public Map<String, String> addfavorite(MemberAccountDTO account,
            @PathVariable("pno") Long pno) {
        log.info("-------------------------");
        log.info("account " +account);
        MemberAccountDTO dto = memberService.getInfoByEmail(account.getEmail());
        log.info(dto.getMno());
        pFavoriteService.incrementFavorite(dto.getMno(), pno);

        return Map.of("result", "Success");

    }

    @DeleteMapping("{pno}/{email}")
    public Map<String, String> deletefavorite(@PathVariable("email") String account,
            @PathVariable("pno") Long pno) {
            
        MemberAccountDTO dto = memberService.getInfoByEmail(account);
        log.info(dto.getMno()+"-----"+ pno);
        pFavoriteService.deleteFavorite(pno,dto.getMno());

        return Map.of("result", "Success");

    }

    @GetMapping("{pno}")
    public Long countfavorite(@PathVariable("pno") Long pno) {

        return pFavoriteService.countFavorite(pno);
    }

    @GetMapping("{pno}/check")
    public Long checkfavorite(MemberAccountDTO account,
            @PathVariable("pno") Long pno) {

        MemberAccountDTO dto = memberService.getInfoByEmail(account.getEmail());

        return pFavoriteService.checkFavorite(pno, dto.getMno());
    }
}
