package com.enlink.authcenter.business.controller;

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

/**
 * Created by someone on 2017/11/15.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthCenterApplication.class)
public class CustomerOauthControllerTest {

	private static final Logger logger = LoggerFactory.getLogger(CustomerOauthControllerTest.class);

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void oauth() throws Exception {

	}

	@Test
	public void getCliToken() throws Exception {
		String url = "/customerOauth/getCliToken?resourceId=bqxz";
		RequestBuilder request = MockMvcRequestBuilders.get(url)
				.header("Authorization",
						"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0X3NlcnZlciIsImV4cCI6MTUxMDczMzE2OCwidG9rZW4iOiJ7XCJjbGllbnRJZFwiOlwidGVzdF9zZXJ2ZXJcIixcImNsaWVudElwXCI6XCIxMjcuMC4wLjFcIixcImNsaWVudFR5cGVcIjoyLFwiZXhwaXJlVGltZVwiOjE1MTA3MzMxNjg5NDksXCJwcml2aWxlZ2VNYXBcIjp7XCJtaWNyb19hdXRoXCI6W1wic2VjcmV0XCJdfX0ifQ.P6_6h_EzZSBx8ELjNq-3ZORBvqpy7ke_pKI6ouoQBj8EaXo72gXZ69NM0mm72TbG67fWCrIkBsAxE0ApenAMlA")
				.contentType(MediaType.APPLICATION_JSON_UTF8);
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		String content = mvcResult.getResponse().getContentAsString();

		Assert.assertEquals("返回成功", 200, status);
		logger.info("content=" + content);
	}

	@Test
	public void getCliTicket() throws Exception {

	}

	@Test
	public void getCliJSTicket() throws Exception {

	}
}