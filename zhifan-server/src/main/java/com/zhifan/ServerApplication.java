package com.zhifan;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenjialing
 */
@Slf4j
@SpringBootApplication
@MapperScan(basePackages = "com.zhifan.**.mapper")
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class,args);
        log.info("成功");
    }

}
