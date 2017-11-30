package com.enlink.authcommon.model.login;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 已签名的登录请求基类
 * @author Timothy
 */
public class BaseSignedLogin extends BaseLogin {

	/** 客户签名 */
	private String clientSign;

	public String getClientSign() {
		return clientSign;
	}

	public void setClientSign(String clientSign) {
		this.clientSign = clientSign;
	}

	/**
	 * 获取授权中心登录请求的参数串
	 * @return 授权中心登录请求的参数串
	 */
	public String getLoginParamStr() {
		return null;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
