package com.enlink.authcenter.auth.model;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.chain.Context;

import com.enlink.authcommon.model.login.BaseSignedLogin;

/**
 * 客户端登录请求上下文类
 * @author Timothy
 */
public class ClientLoginReqContext extends HashMap implements Context {

	/** 登录请求基础对象 */
	private BaseSignedLogin baseSignedLogin;
	/** 登录请求参数Map，主要用于从中取出扩展字段 */
	private Map<String, String> loginReqMap;
	/** 客户端IP地址 */
	private String clientIp;
	/** 微服务token字符串，返回值 */
	private String microTokenStr;

	public ClientLoginReqContext(BaseSignedLogin baseSignedLogin, Map<String, String> loginReqMap, String clientIp) {
		this.baseSignedLogin = baseSignedLogin;
		this.loginReqMap = loginReqMap;
		this.clientIp = clientIp;
	}

	public BaseSignedLogin getBaseSignedLogin() {
		return baseSignedLogin;
	}

	public Map<String, String> getLoginReqMap() {
		return loginReqMap;
	}

	public String getClientIp() {
		return clientIp;
	}

	public String getMicroTokenStr() {
		return microTokenStr;
	}

	public void setMicroTokenStr(String microTokenStr) {
		this.microTokenStr = microTokenStr;
	}
}
