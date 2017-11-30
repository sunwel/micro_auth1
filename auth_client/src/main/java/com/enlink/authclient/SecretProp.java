package com.enlink.authclient;

/**
 * 微服务密钥相关配置<br/>
 * 微服务模块专用，但出于配置方便考虑，放在auth_client模块中
 * @author Timothy
 */
public class SecretProp {

	/** 获取微服务secret的相对URL */
	private String relativeUrl = "/auth/secret";

	/** 获取微服务secret的最大重试次数，默认值：3 */
	private int visitTryMax = 3;

	/** 刷新微服务密钥的间隔时间，单位毫秒，默认值：30000毫秒（30秒） */
	private long refreshInterval = 30000;

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

	public int getVisitTryMax() {
		return visitTryMax;
	}

	public void setVisitTryMax(int visitTryMax) {
		this.visitTryMax = visitTryMax;
	}

	public long getRefreshInterval() {
		return refreshInterval;
	}

	public void setRefreshInterval(long refreshInterval) {
		this.refreshInterval = refreshInterval;
	}
}
