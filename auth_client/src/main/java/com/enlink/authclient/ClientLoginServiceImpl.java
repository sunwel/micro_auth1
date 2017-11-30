package com.enlink.authclient;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.enlink.authcommon.constant.AuthCommonConstants;
import com.enlink.authcommon.model.CodeMsgResult;
import com.enlink.authcommon.model.login.Alg1SignedLogin;
import com.enlink.authcommon.model.login.Alg1UnsignedLogin;
import com.enlink.authcommon.model.login.BaseSignedLogin;

/**
 * 客户端授权中心登录服务<br/>
 * 基于算法1实现
 * @author Timothy
 */
@Service
public class ClientLoginServiceImpl implements IClientLoginService {

	private static final Logger logger = LoggerFactory.getLogger(ClientLoginServiceImpl.class);

	@Autowired
	private AuthClientProp authClientConfig;
	@Autowired
	private NormalRestTemplate restTemplate;

	@Override
	public CodeMsgResult<String> login() {
		logger.info("登录获取微服务Token开始");
		String clientId = authClientConfig.getClientId();
		String clientKey = authClientConfig.getClientKey();
		BaseSignedLogin clientLoginReq = createLoginReq(clientId, clientKey);
		String loginParamStr = clientLoginReq.getLoginParamStr();
		String fullUrl = authClientConfig.getAuthCenterBaseUrl() + authClientConfig.getMicroTokenProp().getRelativeUrl()
				+ "?" + loginParamStr;
		logger.info("登录请求，param=" + loginParamStr);
		String loginResp = restTemplate.getForEntity(fullUrl, String.class).getBody();
		logger.info("登录结果，result=" + loginResp);
		CodeMsgResult<String> codeMsgResult = JSON.parseObject(loginResp, CodeMsgResult.class);
		return codeMsgResult;
	}

	private Alg1SignedLogin createLoginReq(String clientId, String clientKey) {
		// 获取当前时间戳
		long timestamp = System.currentTimeMillis();
		// 随机生成盐值
		int salt = RandomUtils.nextInt(10000000, 100000000);
		// 构造未签名的登录请求
		Alg1UnsignedLogin unsignedLogin = new Alg1UnsignedLogin();
		unsignedLogin.setClientId(clientId);
		unsignedLogin.setClientKey(clientKey);
		unsignedLogin.setAlgId(AuthCommonConstants.LOGIN_ALG1);
		unsignedLogin.setTimestamp(timestamp);
		unsignedLogin.setSalt(salt);
		Alg1SignedLogin signedLogin = unsignedLogin.generateSignedLogin();
		return signedLogin;
	}
}
