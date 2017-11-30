package com.enlink.authclient;

/**
 * 客户端微服务Token相关配置
 * @author Timothy
 */
public class MicroTokenProp {

	/** 获取微服务token相对URL */
	private String relativeUrl = "/auth/token";

	/** 获取微服务token的最大重试次数，默认值：3 */
	private int visitTryMax = 3;

	/** 检查并刷新微服务token的间隔时间，单位毫秒，默认值：300000毫秒（5分钟） */
	private long checkRefreshInterval = 300000;

	/** 微服务token到期前更新的提前时间量，单位毫秒，默认值：1800000毫秒（30分钟） */
	private long refreshBeforeExpire = 1800000;

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

	public long getCheckRefreshInterval() {
		return checkRefreshInterval;
	}

	public void setCheckRefreshInterval(long checkRefreshInterval) {
		this.checkRefreshInterval = checkRefreshInterval;
	}

	public long getRefreshBeforeExpire() {
		return refreshBeforeExpire;
	}

	public void setRefreshBeforeExpire(long refreshBeforeExpire) {
		this.refreshBeforeExpire = refreshBeforeExpire;
	}
}
