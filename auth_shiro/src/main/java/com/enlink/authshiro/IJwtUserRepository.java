package com.enlink.authshiro;

/**
 * 基于JWT的用户持久层服务接口<br/>
 * 本授权中心设计使用JWT自身携带所有用户相关信息，因此基于JWT的用户持久层服务演变为：
 * <ul>
 * <li>通过验证JWT的有效性来判断是否是有效登录</li>
 * <li>通过解析JWT携带的权限信息来赋予用户权限</li>
 * </ul>
 * @author Timothy
 */
public interface IJwtUserRepository {

	/**
	 * 通过验证和解析JWT来模拟从持久层获取用户数据，验证或解析失败返回null
	 * @param jwt JWT
	 * @return IAuthenticationUser
	 */
	JwtRepoAuthenticationToken findByJwt(String jwt);

}
