package io.github.cocodx;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author amazfit
 * @date 2022-08-21 下午10:52
 **/
@MapperScan("io.github.cocodx.mapper")
@SpringBootApplication
public class UpDownApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpDownApplication.class,args);
    }
}
