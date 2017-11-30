package com.enlink.authcommon.model.login;

/**
 * 未签名的登录请求基类
 * @author Timothy
 */
public abstract class BaseUnsignedLogin<T extends BaseSignedLogin> extends BaseLogin {

	/** 客户端Key */
	private String clientKey;

	/**
	 * 产生已签名的登录请求
	 * @return 已签名的登录请求
	 */
	public abstract T generateSignedLogin();

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
}
