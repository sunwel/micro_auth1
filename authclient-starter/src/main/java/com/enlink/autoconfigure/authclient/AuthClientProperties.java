package com.enlink.autoconfigure.authclient;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author hxd
 * @date 2017/11/21
 */

@ConfigurationProperties("enlink.authclient")
public class AuthClientProperties {

	/** Http连接池配置 */
	private HttpConnectionPool httpConnectionPool = new HttpConnectionPool();

	public HttpConnectionPool getHttpConnectionPool() {
		return httpConnectionPool;
	}

	public void setHttpConnectionPool(HttpConnectionPool httpConnectionPool) {
		this.httpConnectionPool = httpConnectionPool;
	}
}
