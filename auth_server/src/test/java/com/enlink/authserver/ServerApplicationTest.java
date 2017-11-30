package com.enlink.authserver;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * server端用于测试的启动类
 */
@ComponentScan(basePackages = { "com.enlink.authshiro", "com.enlink.authserver" })
@SpringBootApplication
public class ServerApplicationTest extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplicationTest.class, args);
	}

	/**
	 * 使用FastJson取得默认的Jackson
	 * @param converters
	 */
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.configureMessageConverters(converters);

		// 初始化转换器
		FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();
		// MediaType[] mediaTypes={MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON};
		fastConvert.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8, MediaType.APPLICATION_JSON));
		// 初始化一个转换器配置
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
		// 将配置设置给转换器并添加到HttpMessageConverter转换器列表中
		fastConvert.setFastJsonConfig(fastJsonConfig);

		converters.add(fastConvert);
	}

}
