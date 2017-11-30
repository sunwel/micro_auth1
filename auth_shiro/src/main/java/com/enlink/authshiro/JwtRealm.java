package com.enlink.authshiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 认证并授权
 * @author Timothy
 */
@Component
public class JwtRealm extends AuthorizingRealm {

	@Autowired
	private IJwtUserRepository userRepository;

	@Override
	public boolean supports(AuthenticationToken token) {
		// 只接受JwtAuthenticationToken类型的token
		return token != null && token instanceof JwtReqAuthenticationToken;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 客户端请求的认证令牌
		JwtReqAuthenticationToken reqToken = (JwtReqAuthenticationToken) token;
		// 服务端持久层查询到的用户信息(获取同时完成令牌验证)
		JwtRepoAuthenticationToken repoToken = userRepository.findByJwt(reqToken.getToken());

		if (repoToken != null) {
			// 取得服务端的用户令牌；第2个参数必须放token
			SimpleAccount account = new SimpleAccount(repoToken, reqToken.getToken(), getName());
			account.addStringPermissions(repoToken.getPermissions());
			return account;
		}
		return null;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		JwtRepoAuthenticationToken user = (JwtRepoAuthenticationToken) principals.getPrimaryPrincipal();
		info.addStringPermissions(user.getPermissions());
		return info;
	}

}
