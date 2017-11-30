package com.enlink.authshiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 客户端JWT登录请求的认证令牌
 * @author Timothy
 */
public class JwtReqAuthenticationToken implements AuthenticationToken {

	private Object userId;
	private String token;

	public JwtReqAuthenticationToken(Object userId, String token) {
		this.userId = userId;
		this.token = token;
	}

	@Override
	public Object getPrincipal() {
		return userId;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

	public Object getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}
}
