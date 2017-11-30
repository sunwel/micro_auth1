package com.enlink.authcommon.model.login;

import com.enlink.authcommon.constant.AuthCommonConstants;

/**
 * 基于算法1的已签名的登录请求类
 * @author Timothy
 */
public class Alg1SignedLogin extends BaseSignedLogin {

	/** 时间戳，授权中心算法1签名验证所需要的参数 */
	private long timestamp;
	/** 盐值，授权中心算法1签名验证所需要的参数 */
	private int salt;

	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * 算法ID固定为1
	 * @return
	 */
	@Override
	public int getAlgId() {
		return AuthCommonConstants.LOGIN_ALG1;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getSalt() {
		return salt;
	}

	public void setSalt(int salt) {
		this.salt = salt;
	}

	@Override
	public String getLoginParamStr() {
		String paramStr = AuthCommonConstants.CENTER_LOGIN_PARAM_CLIENTID + "=" + getClientId();
		paramStr += "&" + AuthCommonConstants.CENTER_LOGIN_PARAM_CLIENTSIGN + "=" + getClientSign();
		paramStr += "&" + AuthCommonConstants.CENTER_LOGIN_PARAM_ALGID + "=" + getAlgId();
		paramStr += "&" + AuthCommonConstants.CENTER_LOGIN_PARAM_REQTIME + "=" + getTimestamp();
		paramStr += "&" + AuthCommonConstants.CENTER_LOGIN_PARAM_SALT + "=" + getSalt();
		return paramStr;
	}
}
