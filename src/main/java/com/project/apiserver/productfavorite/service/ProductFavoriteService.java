package com.project.apiserver.productfavorite.service;

import jakarta.transaction.Transactional;

@Transactional
public interface ProductFavoriteService {
    void incrementFavorite(Long pno, Long mno);
    void deleteFavorite(Long pno, Long mno);
    Long countFavorite(Long pno);
    Long checkFavorite(Long pno, Long mno);
}
