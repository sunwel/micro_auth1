package com.enlink.authshiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.enlink.authcommon.constant.AuthCommonConstants;
import com.enlink.authcommon.constant.ResultStatusCodeEnum;
import com.enlink.authcommon.model.CodeMsgResult;
import com.enlink.authcommon.model.MicroToken;
import com.enlink.authcommon.service.MicroTokenHelper;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * 基于JWT的身份认证filter
 * @author Timothy
 */
@Component
public class JwtAuthenticationFilter extends AuthenticatingFilter {

	private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	@Autowired
	private ISecretService secretService;

	/**
	 * 接口必须实现的方法：创建基于jwt的shiro登录令牌
	 * @param request ServletRequest
	 * @param response ServletResponse
	 * @return jwt的shiro登录令牌
	 * @throws Exception
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		String jwt = getAuthzHeader(request);
		return createToken(jwt);
	}

	/**
	 * 接口必须实现的方法：登录请求是否验证通过
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		boolean loggedIn = false;
		if (isJwtLogin(request)) {
			try {
				loggedIn = executeLogin(request, response);
			} catch (ExpiredJwtException e) {
				logger.error(e.getMessage(), e);
				CodeMsgResult<ResultStatusCodeEnum> result = CodeMsgResult
						.failureResult(ResultStatusCodeEnum.MICROTOKEN_EXPIRED);
				exceptionHandler(response, result);
				return false;
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				CodeMsgResult<ResultStatusCodeEnum> result = CodeMsgResult
						.failureResult(ResultStatusCodeEnum.MICROTOKEN_INVALID);
				exceptionHandler(response, result);
				return false;
			}
		}

		if (!loggedIn) {
			CodeMsgResult<ResultStatusCodeEnum> result = CodeMsgResult
					.failureResult(ResultStatusCodeEnum.MICROTOKEN_INVALID);
			exceptionHandler(response, result);
		}
		return loggedIn;
	}

	/**
	 * 处理异常
	 * @param response
	 * @param result
	 * @throws IOException
	 */
	private void exceptionHandler(ServletResponse response, CodeMsgResult<ResultStatusCodeEnum> result)
			throws IOException {
		// 统一使用FastJson
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		JSON.writeJSONString(response.getOutputStream(), result, SerializerFeature.WriteMapNullValue);
	}

	private boolean isJwtLogin(ServletRequest request) {
		String authzHeader = getAuthzHeader(request);
		return StringUtils.isNotEmpty(authzHeader);
	}

	private String getAuthzHeader(ServletRequest request) {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		return httpRequest.getHeader(AuthCommonConstants.AUTHORIZATION_HEADER);
	}

	/**
	 * 进行jwt的有效性验证，验证通过创建Jwt对应的登录请求令牌，验证失败抛出异常
	 * @param jwt
	 * @return
	 */
	private JwtReqAuthenticationToken createToken(String jwt) {
		MicroToken microToken = MicroTokenHelper.parseJwt(jwt, secretService.getSecretList());
		return new JwtReqAuthenticationToken(microToken.getClientId(), jwt);
	}
}
