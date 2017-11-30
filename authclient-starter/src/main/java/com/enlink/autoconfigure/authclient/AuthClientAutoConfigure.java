package com.enlink.autoconfigure.authclient;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import com.enlink.authclient.MicroTokenInteceptor;
import com.enlink.authclient.MicroTokenRestTemplate;
import com.enlink.authclient.NormalRestTemplate;

/**
 * enlink auth autoconfigure
 * @author hxd
 * @date 2017/11/21.
 */

@EnableConfigurationProperties(AuthClientProperties.class)
public class AuthClientAutoConfigure {

	@Autowired
	private AuthClientProperties authProperties;

	@Bean
	@ConditionalOnMissingBean(MicroTokenRestTemplate.class)
	MicroTokenRestTemplate microTokenRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory,
			MicroTokenInteceptor inteceptor) {
		MicroTokenRestTemplate microTokenRestTemplate = new MicroTokenRestTemplate();
		microTokenRestTemplate.setRequestFactory(clientHttpRequestFactory);
		List<ClientHttpRequestInterceptor> interceptorList = new ArrayList<ClientHttpRequestInterceptor>();
		interceptorList.add(inteceptor);
		microTokenRestTemplate.setInterceptors(interceptorList);
		return microTokenRestTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(NormalRestTemplate.class)
	NormalRestTemplate normalRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
		NormalRestTemplate normalRestTemplate = new NormalRestTemplate();
		normalRestTemplate.setRequestFactory(clientHttpRequestFactory);
		return normalRestTemplate;
	}

	@Bean
	@ConditionalOnMissingBean(ClientHttpRequestFactory.class)
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
		clientConnectionManager.setMaxTotal(authProperties.getHttpConnectionPool().getMaxTotal());
		clientConnectionManager.setDefaultMaxPerRoute(authProperties.getHttpConnectionPool().getDefaultMaxPerRoute());
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(authProperties.getHttpConnectionPool().getSocketTimeOut())
				.setConnectTimeout(authProperties.getHttpConnectionPool().getConnectTimeout()).build();
		if (authProperties.getHttpConnectionPool().isTimeOutOpen()) {
			return new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create()
					.setConnectionManager(clientConnectionManager).setDefaultRequestConfig(requestConfig).build());
		} else {
			return new HttpComponentsClientHttpRequestFactory(
					HttpClientBuilder.create().setConnectionManager(clientConnectionManager).build());
		}
	}

}
