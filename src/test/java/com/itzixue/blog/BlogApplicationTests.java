package com.itzixue.blog;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test_en(){
        //String encrypt = stringEncryptor.encrypt("");
    }
}
