package com.enlink.authcenter.business.controller;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.enlink.authcenter.AuthCenterApplication;
import com.enlink.authcommon.model.CodeMsgResult;

/**
 * Created by someone on 2017/11/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthCenterApplication.class)
public class CustomerOauthControllerMethodTest {

	private static final Logger logger = LoggerFactory.getLogger(CustomerOauthControllerMethodTest.class);
	@Autowired
	CustomerOauthController customerOauthController;

	/**
	 * 前提：需要去掉shiro的权限控制</br>
	 * 暂时无法对带有shiro访问权限的方法进行测试
	 * @throws Exception
	 */
	@Test
	public void oauth() throws Exception {

	}

	/**
	 * 前提：需要去掉shiro的权限控制</br>
	 * 暂时无法对带有shiro访问权限的方法进行测试
	 * @throws Exception
	 */
	@Test
	public void getCliToken() throws Exception {
		CodeMsgResult<Map<Object, Object>> result = customerOauthController.getCliToken("bqxz");
		logger.info("方法执行返回状态为{}", result.getErrCode());
		Assert.assertEquals("方法执行成功", "0", result.getErrCode());
	}

	/**
	 * 前提：需要去掉shiro的权限控制</br>
	 * 暂时无法对带有shiro访问权限的方法进行测试
	 * @throws Exception
	 */
	@Test
	public void getCliTicket() throws Exception {
		CodeMsgResult<Map<Object, Object>> result = customerOauthController.getCliTicket("bqxz");
		logger.info("方法执行返回状态为{}", result.getErrCode());
		Assert.assertEquals("方法执行成功", "0", result.getErrCode());
	}

	/**
	 * 前提：需要去掉shiro的权限控制</br>
	 * 暂时无法对带有shiro访问权限的方法进行测试
	 * @throws Exception
	 */
	@Test
	public void getCliJSTicket() throws Exception {
		CodeMsgResult<Map<Object, Object>> result = customerOauthController.getCliJSTicket("bqxz", "www.baidu.com");
		logger.info("方法执行返回状态为{}", result.getErrCode());
		Assert.assertEquals("方法执行成功", "0", result.getErrCode());
	}
}