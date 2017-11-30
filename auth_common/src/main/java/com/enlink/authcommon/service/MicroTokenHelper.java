package com.enlink.authcommon.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.enlink.authcommon.constant.ResultStatusCodeEnum;
import com.enlink.authcommon.exception.ErrorCodeMsgException;
import com.enlink.authcommon.model.MicroToken;

import io.jsonwebtoken.*;

/**
 * 微服务Token帮助类<br/>
 * 对应关系如下：
 * <ul>
 * <li>microToken.clientId（客户端ID） -> playload.sub（jwt所面向的用户）</li>
 * <li>microToken.expireTime（过期时间） -> playload.exp（ jwt的过期时间）</li>
 * <li>microToken自身转换成json -> playload.token</li>
 * </ul>
 * @author Timothy
 */
public class MicroTokenHelper {

	private static final Logger logger = LoggerFactory.getLogger(MicroTokenHelper.class);
	/** token序列化后对应的主键 */
	private static final String KEY_TOKEN = "token";

	/**
	 * 微服务token对象生成jwt
	 * @param microToken 微服务token
	 * @param secret 密钥
	 * @return jwt
	 */
	public static String createJwt(MicroToken microToken, String secret) {
		String tokenStr = JSON.toJSONString(microToken);
		Map<String, Object> customMap = new HashMap<>();
		customMap.put(KEY_TOKEN, tokenStr);
		String jwt = Jwts.builder()
				// microToken.clientId（客户端ID） -> playload.sub（jwt所面向的用户）
				.setSubject(microToken.getClientId())
				// microToken.expireTime（过期时间） -> playload.exp（ jwt的过期时间）
				.setExpiration(new Date(microToken.getExpireTime()))
				// >microToken自身转换成json -> playload.token
				.addClaims(customMap)
				// 加入密钥
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
		return jwt;
	}

	/**
	 * 解析jwt，生成微服务Token对象
	 * @param jwt json web token
	 * @return 微服务token对象
	 */
	public static MicroToken parseJwt(String jwt, List<String> secretList) {
		Claims claims = null;
		Iterator<String> iterator = secretList.iterator();
		while (claims == null && iterator.hasNext()) {
			String secret = iterator.next();
			// 如果是签名密钥错误，会得到null
			claims = parseJwt(jwt, secret);
		}
		if (claims == null) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.MICROTOKEN_INVALID);
		}
		String microTokenStr = claims.get(KEY_TOKEN, String.class);
		MicroToken microToken = JSON.parseObject(microTokenStr, MicroToken.class);
		if (!microToken.checkValid()) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.MICROTOKEN_INVALID);
		}
		return microToken;
	}

	/**
	 * 解析jwt<br/>
	 * 如果解析过程中是签名密钥异常，返回null；其它异常无需捕捉
	 * @param jwt json web token
	 * @param secret 密钥
	 * @return Claims
	 */
	private static Claims parseJwt(String jwt, String secret) {
		try {
			Jws<Claims> jws = Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(jwt);
			Claims claims = jws.getBody();
			return claims;
		} catch (SignatureException se) {
			logger.error("解析jwt失败，签名错误");
			return null;
		}
	}

	/**
	 * 解析jwt playload，生成微服务Token对象<br/>
	 * 微服务架构中，客户端没有密钥，通过解析playload可以获取相关信息，但不验证jwt的签名
	 * @param jwt json web token
	 * @return 微服务token对象
	 */
	public static MicroToken parseJwtPlayload(String jwt) {
		String[] splitToken = jwt.split("\\.");
		if (splitToken.length != 3) {
			logger.warn("解析jwt playload失败，一个jwt应包含三段内容！jwt={}", jwt);
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.MICROTOKEN_INVALID);
		}
		Jwt<Header, Claims> headerClaims = Jwts.parser().parseClaimsJwt(splitToken[0] + "." + splitToken[1] + ".");
		Claims claims = headerClaims.getBody();
		String microTokenStr = claims.get(KEY_TOKEN, String.class);
		MicroToken microToken = JSON.parseObject(microTokenStr, MicroToken.class);
		if (!microToken.checkValid()) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.MICROTOKEN_INVALID);
		}
		return microToken;
	}
}
