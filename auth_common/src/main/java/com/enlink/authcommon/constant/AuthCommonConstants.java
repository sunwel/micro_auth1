package com.enlink.authcommon.constant;

/**
 * 公用常量定义
 * @author Timothy
 */
public class AuthCommonConstants {

	/** 授权中心登录请求参数名，客户端ID */
	public static final String CENTER_LOGIN_PARAM_CLIENTID = "clientId";
	/** 授权中心登录请求参数名，客户端签名 */
	public static final String CENTER_LOGIN_PARAM_CLIENTSIGN = "clientSign";
	/** 授权中心登录请求参数名，签名算法ID */
	public static final String CENTER_LOGIN_PARAM_ALGID = "algId";
	/** 授权中心登录请求参数名，请求时间 */
	public static final String CENTER_LOGIN_PARAM_REQTIME = "reqTime";
	/** 授权中心登录请求参数名，加密用盐值 */
	public static final String CENTER_LOGIN_PARAM_SALT = "salt";

	/** 登录算法1 */
	public static final int LOGIN_ALG1 = 1;

	/** http header中的授权参数名 */
	public static final String AUTHORIZATION_HEADER = "Authorization";
}
