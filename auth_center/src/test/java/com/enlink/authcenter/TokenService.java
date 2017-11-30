package com.enlink.authcenter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomUtils;
import org.easymock.EasyMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enlink.authcenter.auth.controller.MicroTokenController;
import com.enlink.authcommon.model.CodeMsgResult;
import com.enlink.authcommon.model.login.Alg1SignedLogin;
import com.enlink.authcommon.model.login.Alg1UnsignedLogin;
import com.enlink.authcommon.model.login.BaseSignedLogin;

/**
 * Created by someone on 2017/11/16.
 */
@Component
public class TokenService {

	@Autowired
	MicroTokenController microTokenController;

	public CodeMsgResult<String> getToken(String client, String clientKey) {
		Alg1UnsignedLogin alg1UnsignedLogin = new Alg1UnsignedLogin();
		alg1UnsignedLogin.setClientId(client);
		alg1UnsignedLogin.setClientKey(clientKey);
		alg1UnsignedLogin.setAlgId(1);
		int num = RandomUtils.nextInt(10000000, 100000000);
		alg1UnsignedLogin.setSalt(num);
		long time = System.currentTimeMillis();
		alg1UnsignedLogin.setTimestamp(time);
		Alg1SignedLogin signed = alg1UnsignedLogin.generateSignedLogin();
		HttpServletRequest request = EasyMock.mock(HttpServletRequest.class);
		BaseSignedLogin loginReq = new BaseSignedLogin();
		loginReq.setClientSign(signed.getClientSign());
		loginReq.setAlgId(1);
		loginReq.setClientId(client);
		Map<String, String> loginReqMap = new HashMap<>();
		loginReqMap.put("reqTime", time + "");
		loginReqMap.put("salt", num + "");
		return microTokenController.getNewMicroToken(loginReq, loginReqMap, request);
	}

}
