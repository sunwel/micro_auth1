package com.enlink.authshiro;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 从持久层获取到的基于JWT的用户授权信息<br/>
 * 增加了提供用户权限的方法
 * @author Timothy
 */
public class JwtRepoAuthenticationToken implements AuthenticationToken {

	/** 用户在此微服务中的权限 */
	private Set<String> permissions;
	private Object principal;
	private Object credentials;

	public JwtRepoAuthenticationToken(Set<String> permissions, Object principal, Object credentials) {
		this.permissions = permissions;
		this.principal = principal;
		this.credentials = credentials;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public Object getCredentials() {
		return credentials;
	}
}
