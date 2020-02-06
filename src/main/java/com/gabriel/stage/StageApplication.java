package com.gabriel.stage;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.gabriel.stage.mapper")
@EnableEncryptableProperties
public class StageApplication {

    public static void main(String[] args) {
        SpringApplication.run(StageApplication.class, args);
    }

}
