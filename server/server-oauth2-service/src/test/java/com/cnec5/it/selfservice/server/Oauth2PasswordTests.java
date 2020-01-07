package com.cnec5.it.selfservice.server;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Oauth2PasswordTests {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    public void test_bean_oauth2_password() {
        System.out.println(passwordEncoder.encode("123456"));
    }

}
