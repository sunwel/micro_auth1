package com.enlink.authclient;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.enlink.authcommon.constant.AuthCommonConstants;

/**
 * 微服务请求权限认证参数追加拦截器
 * @author Timothy
 */
@Component
public class MicroTokenInteceptor implements ClientHttpRequestInterceptor {

	@Autowired
	private MicroTokenClientService microTokenClientService;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		// 本拦截器自动在request的头部添加微服务Token
		request.getHeaders().add(AuthCommonConstants.AUTHORIZATION_HEADER, microTokenClientService.getMicroTokenStr());
		return execution.execute(request, body);
	}
}
