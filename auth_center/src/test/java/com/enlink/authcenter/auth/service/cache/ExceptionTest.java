package com.enlink.authcenter.auth.service.cache;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.enlink.authcenter.AuthCenterApplication;
import com.enlink.authcenter.business.controller.CustomerOauthController;
import com.enlink.authcommon.model.CodeMsgResult;

/**
 * Created by someone on 2017/11/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthCenterApplication.class)
@ActiveProfiles("h2")
public class ExceptionTest {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionTest.class);

	@Autowired
	CustomerOauthController customerOauthController;

	/**
	 * 测试内容：Controller层发生异常
	 * 预期：异常抛出
	 * 因为方法级测试，不会被GlobalControllerExceptionHandler处理
	 */
	@Test
	public void exception1() {
		CodeMsgResult<Map<Object, Object>> result;
		try {
			result = customerOauthController.getCliToken("");
		} catch (Exception e) {
			Assert.assertEquals("发生异常", "找不到resourceId", e.getMessage());
		}
		logger.info("发生异常，找不到resourceId");
	}

	/**
	 * 测试内容：非Controller层发生异常
	 * 预期：
	 * 可能需要集成测试
	 */
	@Test
	public void exception2() {
		logger.info("token没有被处理，暂时无法验证");
	}
}
