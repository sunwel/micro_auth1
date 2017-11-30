package com.enlink.autoconfigure.authclient;

/**
 * Http连接池配置
 * @author Timothy
 */
public class HttpConnectionPool {

	/** 超时开关设置 */
	private boolean timeOutOpen = true;
	/** 连接池的最大连接数 */
	private int maxTotal = 200;
	/** 每个路由的最大连接数 */
	private int defaultMaxPerRoute = 20;
	/** HTTP连接请求超时时间 */
	private int connectTimeout = 15000;
	/** HTTP读取数据超时时间 */
	private int socketTimeOut = 30000;

	public boolean isTimeOutOpen() {
		return timeOutOpen;
	}

	public void setTimeOutOpen(boolean timeOutOpen) {
		this.timeOutOpen = timeOutOpen;
	}

	public int getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}

	public int getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}

	public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public int getSocketTimeOut() {
		return socketTimeOut;
	}

	public void setSocketTimeOut(int socketTimeOut) {
		this.socketTimeOut = socketTimeOut;
	}
}
