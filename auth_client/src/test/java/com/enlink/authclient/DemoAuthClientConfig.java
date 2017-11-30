//package com.enlink.authclient;
//
//import org.apache.http.client.config.RequestConfig;
//		import org.apache.http.impl.client.HttpClientBuilder;
//		import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
//		import org.springframework.context.annotation.Bean;
//		import org.springframework.context.annotation.Configuration;
//		import org.springframework.http.client.ClientHttpRequestFactory;
//		import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
//
///**
// * 客户端授权相关配置的Demo实现
// * @author Timothy
// */
//@Configuration
//public class DemoAuthClientConfig {
//
//	@Bean
//	public ClientHttpRequestFactory clientHttpRequestFactory() {
//		PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
//		clientConnectionManager.setMaxTotal(200);
//		clientConnectionManager.setDefaultMaxPerRoute(20);
//		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(15000).build();
//		return new HttpComponentsClientHttpRequestFactory(
//				HttpClientBuilder.create().setConnectionManager(clientConnectionManager)
//						// .setDefaultRequestConfig(requestConfig)
//						.build());
//	}
//}
