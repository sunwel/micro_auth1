package com.enlink.authcommon.model.login;

import org.apache.commons.codec.digest.DigestUtils;

import com.enlink.authcommon.constant.AuthCommonConstants;

/**
 * 基于算法1的未签名的登录请求类
 * @author Timothy
 */
public class Alg1UnsignedLogin extends BaseUnsignedLogin<Alg1SignedLogin> {

	/** 时间戳，授权中心算法1签名验证所需要的参数 */
	private long timestamp;
	/** 盐值，授权中心算法1签名验证所需要的参数 */
	private int salt;

	@Override
	public Alg1SignedLogin generateSignedLogin() {
		String timeStr = timestamp + "";
		String time4SignPart = timeStr.substring(timeStr.length() - 4, timeStr.length());
		// 待加密字符串结构： clientId+","+clientKey+":"+时间戳后4位+"@"+salt
		String signBefore = this.getClientId() + "," + this.getClientKey() + ":" + time4SignPart + "@" + salt;
		String sign = DigestUtils.md5Hex(signBefore);

		Alg1SignedLogin signedLogin = new Alg1SignedLogin();
		signedLogin.setClientId(this.getClientId());
		signedLogin.setClientSign(sign);
		signedLogin.setAlgId(AuthCommonConstants.LOGIN_ALG1);
		signedLogin.setTimestamp(this.getTimestamp());
		signedLogin.setSalt(this.getSalt());
		return signedLogin;
	}

	public long getTimestamp() {
		return timestamp;
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
}
