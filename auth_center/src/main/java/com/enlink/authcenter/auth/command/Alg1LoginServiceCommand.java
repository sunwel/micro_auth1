package com.enlink.authcenter.auth.command;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.enlink.authcommon.constant.AuthCommonConstants;
import com.enlink.authcommon.constant.ResultStatusCodeEnum;
import com.enlink.authcommon.exception.ErrorCodeMsgException;
import com.enlink.authcommon.model.login.Alg1SignedLogin;
import com.enlink.authcommon.model.login.Alg1UnsignedLogin;
import com.enlink.authcommon.model.login.BaseSignedLogin;

/**
 * 基于算法1的授权中心登录服务命令类
 * @author Timothy
 */
@Component
public class Alg1LoginServiceCommand extends BaseLoginServiceCommand<Alg1UnsignedLogin, Alg1SignedLogin> {

	/** 请求时间戳与当前时间的误差范围，单位毫秒 */
	private static final int REQTIME_ERROR_RANGE = 60000;

	@Override
	public int getAlgId() {
		return AuthCommonConstants.LOGIN_ALG1;
	}

	@Override
	public Alg1SignedLogin getRealSignedLogin(BaseSignedLogin baseSignedLogin, Map<String, String> loginReqMap) {
		// 需要两个扩展参数请求时间，盐值
		if (!StringUtils.isNumeric(loginReqMap.get(AuthCommonConstants.CENTER_LOGIN_PARAM_REQTIME))) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.REQTIME_INVALID);
		}
		if (!StringUtils.isNumeric(loginReqMap.get(AuthCommonConstants.CENTER_LOGIN_PARAM_SALT))) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.SALT_INVALID);
		}

		long reqTime = Long.parseLong(loginReqMap.get(AuthCommonConstants.CENTER_LOGIN_PARAM_REQTIME));
		int salt = Integer.parseInt(loginReqMap.get(AuthCommonConstants.CENTER_LOGIN_PARAM_SALT));
		// 请求时间与当前时间的误差要在规定范围内
		if (reqTime + REQTIME_ERROR_RANGE < System.currentTimeMillis()
				|| reqTime - REQTIME_ERROR_RANGE > System.currentTimeMillis()) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.REQTIME_INVALID);
		}
		Alg1SignedLogin clientLoginReq = new Alg1SignedLogin();
		clientLoginReq.setClientId(baseSignedLogin.getClientId());
		clientLoginReq.setClientSign(baseSignedLogin.getClientSign());
		clientLoginReq.setAlgId(baseSignedLogin.getAlgId());
		clientLoginReq.setTimestamp(reqTime);
		clientLoginReq.setSalt(salt);
		return clientLoginReq;
	}

	@Override
	public Alg1UnsignedLogin generateUnsignedLogin(Alg1SignedLogin signedLogin, String clientKey) {
		Alg1UnsignedLogin unsignedLogin = new Alg1UnsignedLogin();
		unsignedLogin.setClientId(signedLogin.getClientId());
		unsignedLogin.setClientKey(clientKey);
		unsignedLogin.setAlgId(signedLogin.getAlgId());
		unsignedLogin.setTimestamp(signedLogin.getTimestamp());
		unsignedLogin.setSalt(signedLogin.getSalt());
		return unsignedLogin;
	}
}
