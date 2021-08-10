package com.source;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Source
 * @date 2021/08/02/9:43
 */
@SpringBootApplication
@MapperScan("com.source.mapper")
@EnableTransactionManagement
public class boot {
    public static void main(String[] args) {
        SpringApplication.run(boot.class);
    }
}
