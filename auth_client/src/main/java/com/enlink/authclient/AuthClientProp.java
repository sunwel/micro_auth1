package com.enlink.authclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 客户端认证请求配置类
 * @author Timothy
 */
@Component
@ConfigurationProperties(prefix = "authclient")
public class AuthClientProp {

	/** 客户端ID，必须配置 */
	private String clientId;

	/** 客户端登录Key，必须配置 */
	private String clientKey;

	/** 授权中心基础地址，必须配置 */
	private String authCenterBaseUrl;

	/** 客户端微服务Token相关配置，一般使用默认值即可 */
	private MicroTokenProp microTokenProp = new MicroTokenProp();

	/** 微服务密钥相关配置，一般使用默认值即可 */
	private SecretProp secretProp = new SecretProp();

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getAuthCenterBaseUrl() {
		return authCenterBaseUrl;
	}

	public void setAuthCenterBaseUrl(String authCenterBaseUrl) {
		this.authCenterBaseUrl = authCenterBaseUrl;
	}

	public MicroTokenProp getMicroTokenProp() {
		return microTokenProp;
	}

	public void setMicroTokenProp(MicroTokenProp microTokenProp) {
		this.microTokenProp = microTokenProp;
	}

	public SecretProp getSecretProp() {
		return secretProp;
	}

	public void setSecretProp(SecretProp secretProp) {
		this.secretProp = secretProp;
	}
}
