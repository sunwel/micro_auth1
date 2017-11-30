package com.enlink.authcommon.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微服务Token类<br/>
 * 暂不考虑加入secretId，否则必须解析playload才能得到这个值
 * @author idea
 */
public class MicroToken {

	private static final Logger logger = LoggerFactory.getLogger(MicroToken.class);

	/** 客户端ID */
	private String clientId;
	/** 客户端类型 */
	private int clientType;
	/** 客户端IP */
	private String clientIp;
	/** 客户端权限集 */
	private Map<String, List<String>> privilegeMap;
	/** 此微服务Token的过期时间戳 */
	private long expireTime;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public Map<String, List<String>> getPrivilegeMap() {
		return privilegeMap;
	}

	public void setPrivilegeMap(Map<String, List<String>> privilegeMap) {
		this.privilegeMap = privilegeMap;
	}

	public int getClientType() {
		return clientType;
	}

	public void setClientType(int clientType) {
		this.clientType = clientType;
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public boolean checkValid() {
		if (StringUtils.isBlank(clientId)) {
			logger.warn("微服务Token的clientId错误，clientId={}", this.clientId);
			return false;
		} else if (clientType < 0) {
			logger.warn("微服务Token的clientType错误，clientType={}", this.clientType);
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "MicroToken{" + "clientId='" + clientId + '\'' + ", clientType=" + clientType + ", clientIp='" + clientIp
				+ '\'' + ", privilegeMap=" + privilegeMap + ", expireTime=" + expireTime + '}';
	}
}
