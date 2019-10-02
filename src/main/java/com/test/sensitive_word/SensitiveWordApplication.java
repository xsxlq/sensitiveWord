package com.test.sensitive_word;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.test.sensitive_word.dao")
public class SensitiveWordApplication {

    public static void main(String[] args) {
        SpringApplication.run(SensitiveWordApplication.class, args);
    }

}
