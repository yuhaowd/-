package com.zshs.messagecenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.zshs.messagecenter.mapper")
public class MessageCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageCenterApplication.class, args);
    }

}
