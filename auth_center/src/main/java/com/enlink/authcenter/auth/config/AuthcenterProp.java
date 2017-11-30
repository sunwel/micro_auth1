package com.enlink.authcenter.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Timothy on 2017/10/31.
 */
@Component
@ConfigurationProperties(prefix = "enlink.authcenter.auth")
public class AuthcenterProp {

	/** token允许生存的最大时间，单位毫秒，默认为2小时 */
	private int tokenAliveMillisec = 2 * 60 * 60 * 1000;

	/** 缓存刷新的间隔时间，单位毫秒，默认值：60000毫秒（1分钟） */
	private long cacheRefreshFixedRate = 60000;

	/** 缓存刷新检查的间隔时间，单位毫秒，默认值：5000毫秒（5秒） */
	private long cacheRefreshCheckFixedRate = 5000;

	public int getTokenAliveMillisec() {
		return tokenAliveMillisec;
	}

	public void setTokenAliveMillisec(int tokenAliveMillisec) {
		this.tokenAliveMillisec = tokenAliveMillisec;
	}

	public long getCacheRefreshFixedRate() {
		return cacheRefreshFixedRate;
	}

	public void setCacheRefreshFixedRate(long cacheRefreshFixedRate) {
		this.cacheRefreshFixedRate = cacheRefreshFixedRate;
	}

	public long getCacheRefreshCheckFixedRate() {
		return cacheRefreshCheckFixedRate;
	}

	public void setCacheRefreshCheckFixedRate(long cacheRefreshCheckFixedRate) {
		this.cacheRefreshCheckFixedRate = cacheRefreshCheckFixedRate;
	}
}
