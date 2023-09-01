package com.project.apiserver.boardfavorite.service;

import jakarta.transaction.Transactional;

@Transactional
public interface BoardFavoriteService {
    
    void incrementFavorite(Long bno, Long mno);
    void deleteFavorite(Long bno, Long mno);
    Long countFavorite(Long bno);
    Long checkFavorite(Long bno, Long mno);
}
