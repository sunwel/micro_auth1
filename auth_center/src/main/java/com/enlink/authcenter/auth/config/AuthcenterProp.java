package com.enlink.authcenter.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Timothy on 2017/10/31.
 */
@Component
@ConfigurationProperties(prefix = "authcenter.auth")
public class AuthcenterProp {

	/** token允许生存的最大时间，单位毫秒，默认为2小时 */
	private int tokenAliveMillisec = 2 * 60 * 60 * 1000;

	public int getTokenAliveMillisec() {
		return tokenAliveMillisec;
	}

	public void setTokenAliveMillisec(int tokenAliveMillisec) {
		this.tokenAliveMillisec = tokenAliveMillisec;
	}
}
