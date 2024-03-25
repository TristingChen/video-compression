package com.zhifan;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author chenjialing
 */
@Slf4j
@SpringBootApplication
@EnableScheduling
@ComponentScan(value = {"com.zhifan.*","io.renren.*"})
@MapperScan(basePackages = {"com.zhifan.**.mapper","io.renren.**.dao"})
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class,args);
    }

}
