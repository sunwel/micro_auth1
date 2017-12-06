package com.enlink.authcenter;

import java.util.Arrays;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@MapperScan("com.enlink.auth.dao.mapper*")
@ImportResource(locations = { "classpath:generator_web_db.xml" })
@ComponentScan(basePackages = { "com.enlink.authcenter", "com.enlink.auth.dao", "com.enlink.authshiro",
		"com.enlink.authserver" })
@EnableScheduling
@EnableEurekaClient
@SpringBootApplication
public class AuthCenterApplication {

	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		fastConverter
				.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
		fastConverter.setFastJsonConfig(fastJsonConfig);
		HttpMessageConverter<?> converter = fastConverter;
		return new HttpMessageConverters(converter);
	}

	public static void main(String[] args) {
		SpringApplication.run(AuthCenterApplication.class, args);
	}
}
