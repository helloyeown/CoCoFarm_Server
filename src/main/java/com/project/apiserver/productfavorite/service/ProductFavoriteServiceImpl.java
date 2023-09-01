package com.project.apiserver.productfavorite.service;

import org.springframework.stereotype.Service;

import com.project.apiserver.productfavorite.repository.ProductFavoriteRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductFavoriteServiceImpl implements ProductFavoriteService{
    private final ProductFavoriteRepository pFavoriteRepository;

    @Override
    public void incrementFavorite(Long pno, Long mno) {
        pFavoriteRepository.insertFavorite(pno, mno);
    }

    @Override
    public void deleteFavorite(Long pno, Long mno) {
        log.info("--------------------------");
       pFavoriteRepository.deleteFavorite(pno, mno);
    }

    @Override
    public Long countFavorite(Long pno) {
        
        return pFavoriteRepository.countFavorite(pno);
    }

    @Override
    public Long checkFavorite(Long pno, Long mno) {
        
        return pFavoriteRepository.checkFavorite(pno, mno);
    }
    
}
