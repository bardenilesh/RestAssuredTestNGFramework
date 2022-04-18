package com.spotify.oauth2.tests;

import org.testng.annotations.BeforeMethod;

import java.lang.reflect.Method;

public class BaseTest {
    @BeforeMethod
    public void start(Method m){
        System.out.println("Method name is "+m.getName());
        System.out.println("Thread name is "+Thread.currentThread().getId());
    }
}
