package com.enlink.authcenter.auth.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.enlink.auth.dao.service.ITlClientService;
import com.enlink.authcenter.AuthCenterApplication;
import com.enlink.authcenter.CacheRefreshUtil;
import com.enlink.authcenter.TokenService;
import com.enlink.authcenter.auth.service.cache.ClientCache;
import com.enlink.authcommon.model.CodeMsgResult;

/**
 * Created by someone on 2017/11/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AuthCenterApplication.class)
@ActiveProfiles("h2")
public class MicroTokenControllerMethodTest {

	private static final Logger logger = LoggerFactory.getLogger(MicroTokenControllerMethodTest.class);

	@Autowired
	ClientCache clientCache;
	@Autowired
	private ITlClientService clientService;
	@Autowired
	CacheRefreshUtil cacheRefreshUtil;
	@Autowired
	TokenService tokenService;

	/**
	 * HttpServletRequest对象采用mock方法创建，暂时没能设置ip等内容</br>
	 * 所以在测试的时候需要修改getNewMicroToken方法内的ip地址获取等内容
	 * @throws Exception
	 */
	@Test
	public void getNewMicroToken() {
		String clientStr = "test_server";
		String clientKeyStr = "test_server_key1";
		CodeMsgResult<String> result = tokenService.getToken(clientStr, clientKeyStr);
		logger.info("返回状态为{},返回token为{}", result.getErrCode(), result.getResult());
		Assert.assertEquals("获取token成功", "0", result.getErrCode());
	}

}