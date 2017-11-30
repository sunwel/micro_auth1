package com.enlink.authcenter.auth.command;

import java.util.Map;

import org.apache.commons.chain.Context;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.enlink.auth.dao.entity.TlClient;
import com.enlink.authcenter.auth.config.AuthcenterProp;
import com.enlink.authcenter.auth.model.ClientLoginReqContext;
import com.enlink.authcenter.auth.service.ClientPrivilegeService;
import com.enlink.authcenter.auth.service.cache.ClientCache;
import com.enlink.authcenter.auth.service.cache.SecretCache;
import com.enlink.authcommon.constant.ResultStatusCodeEnum;
import com.enlink.authcommon.exception.ErrorCodeMsgException;
import com.enlink.authcommon.model.MicroToken;
import com.enlink.authcommon.model.login.BaseSignedLogin;
import com.enlink.authcommon.model.login.BaseUnsignedLogin;
import com.enlink.authcommon.service.MicroTokenHelper;

/**
 * 授权中心登录服务命令基础类
 * @author Timothy
 */
public abstract class BaseLoginServiceCommand<T1 extends BaseUnsignedLogin<T2>, T2 extends BaseSignedLogin>
		implements ILoginServiceCommand {

	private static final Logger logger = LoggerFactory.getLogger(BaseLoginServiceCommand.class);

	/** 客户端类型为管理员的长量值 */
	private static final int CLIENT_TYPE_ADMIN = 99;

	@Autowired
	private ClientCache clientCache;
	@Autowired
	private ClientPrivilegeService clientPrivilegeService;
	@Autowired
	private SecretCache secretCache;
	@Autowired
	private AuthcenterProp authcenterProp;

	@Override
	public boolean execute(Context context) throws Exception {
		if (!(context instanceof ClientLoginReqContext)) {
			String errorMsg = "上下文类型不正确，期望类型是ClientLoginReqContext类，请检查系统配置！";
			logger.warn(errorMsg);
			throw new Exception(errorMsg);
		}
		// 获取登录请求上下文
		ClientLoginReqContext loginReqContext = (ClientLoginReqContext) context;
		// 登录请求上下文中读取已签名登录请求基础对象
		BaseSignedLogin baseSignedLogin = loginReqContext.getBaseSignedLogin();
		Map<String, String> loginReqMap = loginReqContext.getLoginReqMap();
		// 判断是否是本命令所支持的验证算法
		if (baseSignedLogin.getAlgId() == getAlgId()) {
			// 获取真正的登录请求对象
			T2 realSignedLogin = getRealSignedLogin(baseSignedLogin, loginReqMap);
			// 获取客户端信息
			TlClient client = getClient(baseSignedLogin.getClientId());
			// 验证签名
			validateClientSign(realSignedLogin, client);
			// 验证通过，创建MicroToken
			String newMicroTokenStr = getNewMicroTokenStr(client, loginReqContext.getClientIp());
			loginReqContext.setMicroTokenStr(newMicroTokenStr);
			return true;
		}
		return false;
	}

	/**
	 * 获取clientId对应的客户端信息
	 * @param clientId 客户端ID
	 * @return 客户端信息
	 */
	private TlClient getClient(String clientId) {
		TlClient client = clientCache.getValidDataMap().get(clientId);
		if (client == null) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.CLIENTID_INVALID);
		}
		return client;
	}

	/**
	 * 验证签名
	 * @param realSignedLogin
	 * @param client
	 */
	private void validateClientSign(T2 realSignedLogin, TlClient client) {
		// 首先取clientKey1进行验证，如失败取clientKey2进行验证
		if (!validateClientSign(realSignedLogin, client.getClientKey1())
				&& !validateClientSign(realSignedLogin, client.getClientKey2())) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.CLIENTSIGN_INVALID);
		}
	}

	/**
	 * 验证签名
	 * @param sourceSignedLogin 待验证签名登录对象
	 * @param clientKey 客户端Key
	 * @return 验证结果
	 */
	private boolean validateClientSign(T2 sourceSignedLogin, String clientKey) {
		if (StringUtils.isEmpty(clientKey)) {
			return false;
		}
		T1 unsignedLogin = generateUnsignedLogin(sourceSignedLogin, clientKey);
		T2 targetSignedLogin = unsignedLogin.generateSignedLogin();
		return sourceSignedLogin.getClientSign().equals(targetSignedLogin.getClientSign());
	}

	/**
	 * 产生新的微服务Token字符串
	 * @param client 客户端信息
	 * @param clientIp 客户端IP
	 * @return 新的微服务Token字符串
	 */
	private String getNewMicroTokenStr(TlClient client, String clientIp) {
		MicroToken microToken = new MicroToken();
		microToken.setClientId(client.getClientId());
		microToken.setClientType(client.getClientType());
		microToken.setClientIp(clientIp);
		boolean isAdmin = CLIENT_TYPE_ADMIN == client.getClientType().intValue() ? true : false;
		microToken.setPrivilegeMap(clientPrivilegeService.getClientPrivilege(client.getClientId(), isAdmin));
		// 设置过期时间
		microToken.setExpireTime(System.currentTimeMillis() + authcenterProp.getTokenAliveMillisec());
		return MicroTokenHelper.createJwt(microToken, getSecret());
	}

	private String getSecret() {
		return secretCache.getValidDataMap().values().iterator().next().getSecret();
	}

	/**
	 * 获取这个命令所支持的算法ID
	 * @return 这个命令所支持的算法ID
	 */
	public abstract int getAlgId();

	/**
	 * 获取真正的已签名登录请求对象
	 * @param baseSignedLogin 已签名登录请求基本对象
	 * @param loginReqMap 扩展参数
	 * @return 真正的已签名登录请求对象
	 */
	public abstract T2 getRealSignedLogin(BaseSignedLogin baseSignedLogin, Map<String, String> loginReqMap);

	public abstract T1 generateUnsignedLogin(T2 signedLogin, String clientKey);
}
