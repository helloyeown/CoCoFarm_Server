package com.project.apiserver.boardfavorite.service;

import org.springframework.stereotype.Service;

import com.project.apiserver.boardfavorite.repository.BoardFavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardFavoriteServiceImpl implements BoardFavoriteService {

    private final BoardFavoriteRepository bFavoriteRepository;

    @Override
    public void incrementFavorite(Long bno, Long mno) {
        bFavoriteRepository.insertSubscriptionNative(bno, mno);
    }

    @Override
    public void deleteFavorite(Long bno, Long mno) {
        bFavoriteRepository.deleteSubscriptionNative(bno, mno);
    }

    @Override
    public Long countFavorite(Long bno) {
        return bFavoriteRepository.countSub(bno);
    }

    @Override
    public Long checkFavorite(Long bno, Long mno) {
       
        return bFavoriteRepository.checkFavorite(bno, mno);

    }
    
}
