package com.enlink.authclient;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 包含微服务Token的专用RestTemplate<br/>
 * 使用此RestTemplate需要：<br/>
 * 提供ClientHttpRequestFactory类型的Bean，建议使用HttpCommons提供的基于连接池的实例
 * @author Timothy
 */
@Component
public class MicroTokenRestTemplate extends RestTemplate {

	@Autowired
	private ClientHttpRequestFactory httpRequestFactory;
	@Autowired
	private MicroTokenInteceptor microTokenInteceptor;

	@PostConstruct
	public void init() {
		this.setRequestFactory(httpRequestFactory);
		// 设置加载token的拦截器
		this.setInterceptors(Arrays.asList(microTokenInteceptor));
	}
}
