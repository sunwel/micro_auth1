package com.enlink.authcs;

        import org.springframework.boot.SpringApplication;
        import org.springframework.boot.autoconfigure.SpringBootApplication;
        import org.springframework.context.annotation.ComponentScan;
        import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by someone on 2017/11/6.
 */
@ComponentScan(basePackages = {"com.enlink.authcs","com.enlink.authserver","com.enlink.authclient","com.enlink.authshiro"})
@EnableScheduling
@SpringBootApplication
public class AuthClientServerAppTest {
    public static void main(String[] args) {
        SpringApplication.run(AuthClientServerAppTest.class,args);
    }
}
