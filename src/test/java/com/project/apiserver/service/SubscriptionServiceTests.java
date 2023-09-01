package com.project.apiserver.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.project.apiserver.subscription.service.SubscriptionService;

@SpringBootTest
public class SubscriptionServiceTests {
    
    @Autowired
    SubscriptionService subService;


    @Test
    public void delteTest(){

        subService.deleteSub(4L, 483L);
    }
}
