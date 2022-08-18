package com.jellyb3ar.polly;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConfigureAnnotationTest {
    @Value("${abc}")
    private String test;

    @Test
    public void valueAnnotationTest(){
        System.out.println("["+test+"]");
        Assert.assertEquals(test, "test");
    }
}
