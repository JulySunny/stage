package com.gabriel.stage;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;

@Slf4j
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
@MapperScan(basePackages = "com.gabriel.stage.mapper")
public class StageApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext application = SpringApplication.run(StageApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        //String path = env.getProperty("server.servlet.context-path");
        String active = env.getProperty("spring.profiles.active");
        String maxFileSize = env.getProperty("spring.servlet.multipart.max-file-size"); //最大文件大小
        String maxRequestSize = env.getProperty("spring.servlet.multipart.max-request-size"); //最大请求大小
        log.info("\n----------------------------------------------------------\n\t" +
                "Application is running! Access URLs:\n\t" +
                "Doc: \t\thttp://" + ip + ":" + port + "/doc.html\n\t" +
                "spring-profiles-active: \t\t" + active + "\n\t" +
                "max-file-size: \t\t" + maxFileSize + "\n\t" +
                "max-request-size: \t\t" + maxRequestSize + "\n" +
                "----------------------------------------------------------");
    }

}
