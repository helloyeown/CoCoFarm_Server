package com.project.apiserver.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.apiserver.common.Category;
import com.project.apiserver.common.CategoryRepository;

@SpringBootTest
public class CategoryRepositoryTests {
    

    @Autowired(required = false)
    private CategoryRepository categoryRepository;

    @Test
    public void insertTest(){
    
        Category category = Category.builder().catename("공지사항").build();

        categoryRepository.save(category);
    }
    
}
