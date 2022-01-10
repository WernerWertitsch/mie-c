package com.wecreate.miec;

import com.wecreate.miec.example.ExampleContext;
import org.springframework.boot.SpringApplication;

public class TestMain {
    public static void main(String[] args) {
        new TestMain();
    }

    public TestMain() {
        ExampleContext context = new ExampleContext();
        System.out.println(context.printAllDefinitions().toString());
    }

}
