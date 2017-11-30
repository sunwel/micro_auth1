package com.enlink.authcommon.model.login;

/**
 * 登录请求基类
 * @author Timothy
 */
public class BaseLogin {

	/** 客户端ID */
	private String clientId;
	/** 算法ID */
	private int algId;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getAlgId() {
		return algId;
	}

	public void setAlgId(int algId) {
		this.algId = algId;
	}
}
