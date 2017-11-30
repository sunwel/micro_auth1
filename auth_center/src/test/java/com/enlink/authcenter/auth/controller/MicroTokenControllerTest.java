package com.enlink.authcenter.auth.controller;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.enlink.authcenter.AuthCenterApplication;
import com.enlink.authcommon.model.login.Alg1SignedLogin;
import com.enlink.authcommon.model.login.Alg1UnsignedLogin;

/**
 * Created by someone on 2017/11/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthCenterApplication.class)
public class MicroTokenControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(MicroTokenControllerTest.class);

	// @Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		// MockMvcBuilders使用构建MockMvc对象 （项目拦截器有效）
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		// 单个类 拦截器无效
		// mockMvc = MockMvcBuilders.standaloneSteup(userController).build();
	}

	@Test
	public void getNewMicroToken() throws Exception {

		Alg1UnsignedLogin alg1UnsignedLogin = new Alg1UnsignedLogin();
		alg1UnsignedLogin.setClientId("test_server");
		alg1UnsignedLogin.setClientKey("test_server_key1");
		alg1UnsignedLogin.setAlgId(1);
		alg1UnsignedLogin.setSalt(RandomUtils.nextInt(10000000, 100000000));
		alg1UnsignedLogin.setTimestamp(System.currentTimeMillis());
		Alg1SignedLogin signed = alg1UnsignedLogin.generateSignedLogin();

		String url = "/auth/token?clientId=" + signed.getClientId() + "&clientSign=" + signed.getClientSign()
				+ "&algId=" + signed.getAlgId() + "&reqTime=" + signed.getTimestamp() + "&salt=" + signed.getSalt();
		RequestBuilder request = MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_UTF8);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();
		logger.info("返回状态为{},返回内容为{}", status, content);
		Assert.assertTrue("正确", status == 200);
	}
}