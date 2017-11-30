package com.enlink.authclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.enlink.authcommon.model.CodeMsgResult;

/**
 * server端启动类的
 */
@Component
public class ClientBusinessTest {

	@Autowired
	private MicroTokenRestTemplate tokenRestTemplate;

	public void testGetRandomInt() {
		String url = "http://localhost:8088/test/getRandomInt";
		CodeMsgResult<String> codeMsgResult = tokenRestTemplate.getForObject(url, CodeMsgResult.class);
		System.out.println(JSON.toJSON(codeMsgResult));
	}

	public void testGetRandomLong() {
		String url = "http://localhost:8088/test/getRandomLong";
		CodeMsgResult<String> codeMsgResult = tokenRestTemplate.getForObject(url, CodeMsgResult.class);
		System.out.println(JSON.toJSON(codeMsgResult));
	}

	public void getRandomPriv3() {
		String url = "http://localhost:8088/test/getRandomPriv3";
		CodeMsgResult<String> codeMsgResult = tokenRestTemplate.getForObject(url, CodeMsgResult.class);
		System.out.println(JSON.toJSON(codeMsgResult));
	}

	public void testGetSecretString() {
		String url = "http://localhost:8080/auth/secret";
		CodeMsgResult<String> codeMsgResult = tokenRestTemplate.getForObject(url, CodeMsgResult.class);
		System.out.println(JSON.toJSON(codeMsgResult));
	}

	public void testGetTest() {
		String url = "http://localhost:8080/customerOauth/getCliTicket?resourceId=bqxz";
		CodeMsgResult<String> codeMsgResult = tokenRestTemplate.getForObject(url, CodeMsgResult.class);
		System.out.println(JSON.toJSON(codeMsgResult));
	}
}
