package com.enlink.auth.dao;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by someone on 2017/9/22.
 */
@MapperScan("com.enlink.auth.dao.mapper*")
@ImportResource(locations = { "classpath:generator_web_db.xml" })
@SpringBootApplication
public class ApplicationTest {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ApplicationTest.class);
       /*
        * 对于使用过Spring Boot的开发者来说，程序启动的时候输出的由字符组成的Spring符号并不陌生。
        * 这个是Spring Boot为自己设计的Banner：如果有人不喜欢这个输出，本文说一下怎么修改。
        * Banner.Mode.OFF:关闭;
        * Banner.Mode.CONSOLE:控制台输出，默认方式;
        * Banner.Mode.LOG:日志输出方式;
        */
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
