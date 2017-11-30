package com.enlink.authserver;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 服务端配置信息类
 * @author Timothy
 */
@Component
@ConfigurationProperties(prefix = "enlink.authserver")
public class AuthServerProp {

	/** 微服务名，微服务系统必配字段 */
	@NotNull
	private String serverName;

	/** 需要进行权限检查的路径集合 */
	private List<String> needMicroTokenCheckPaths;

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public List<String> getNeedMicroTokenCheckPaths() {
		return needMicroTokenCheckPaths;
	}

	public void setNeedMicroTokenCheckPaths(List<String> needMicroTokenCheckPaths) {
		this.needMicroTokenCheckPaths = needMicroTokenCheckPaths;
	}
}
