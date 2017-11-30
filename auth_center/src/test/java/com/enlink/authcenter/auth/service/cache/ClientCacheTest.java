package com.enlink.authcenter.auth.service.cache;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.enlink.auth.dao.entity.TlClient;
import com.enlink.auth.dao.service.ITlClientService;
import com.enlink.authcenter.AuthCenterApplication;
import com.enlink.authcenter.CacheRefreshUtil;
import com.enlink.authcenter.TokenService;
import com.enlink.authcommon.constant.AuthCommonConstants;
import com.enlink.authcommon.exception.ErrorCodeMsgException;
import com.enlink.authcommon.model.CodeMsgResult;

/**
 * Created by Timothy on 2017/11/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthCenterApplication.class)
@ActiveProfiles("h2")
public class ClientCacheTest {

	private static final Logger logger = LoggerFactory.getLogger(ClientCacheTest.class);

	@Autowired
	ClientCache clientCache;
	@Autowired
	private ITlClientService clientService;
	@Autowired
	CacheRefreshUtil cacheRefreshUtil;
	@Autowired
	TokenService tokenService;

	@Test
	// @Transactional
	// @Rollback(true)
	public void test1() {
		// tl_client表初始有6条数据，其中5条有效数据，1条无效数据
		// tl_client表初始有效数据个数定义
		int initValidDataSize = 5;
		// 创建一条新的Client
		TlClient client = new TlClient();
		client.setClientId("temp1").setClientKey1("temp1_key").setClientType(1).setClientStatus(1);
		clientService.insert(client);
		cacheRefreshUtil.clientCacheRefresh();
		Map<String, TlClient> validData = clientCache.getValidDataMap();
		Assert.assertEquals(validData.size(), initValidDataSize + 1);
		clientService.delete(new EntityWrapper<>(client));
		cacheRefreshUtil.clientCacheRefresh();
		logger.info(clientCache.getValidDataMap().size() + "");
	}

	@Test
	public void test2() {
		// tl_client表初始有6条数据，其中5条有效数据，1条无效数据
		// tl_client表初始有效数据个数定义
		int initValidDataSize = 5;
		cacheRefreshUtil.clientCacheRefresh();
		Map<String, TlClient> validData = clientCache.getValidDataMap();
		Assert.assertEquals(validData.size(), initValidDataSize);
	}

	/**
	 * 缓存客户端数量=数据库客户端数量-无效客户端数量
	 */
	@Test
	public void test3() {
		List<TlClient> list = clientService.selectList(new EntityWrapper<>());
		logger.info("客户端数量：size={}", list.size());
		Wrapper<TlClient> wrapper = new EntityWrapper<>();
		wrapper.ne(TlClient.CLIENT_STATUS, 1);
		List<TlClient> list1 = clientService.selectList(wrapper);
		logger.info("无效客户端数量：size={}", list1.size());
		Map<String, TlClient> map = clientCache.getValidDataMap();
		logger.info("缓存客户端数量：size={}", map.size());
		logger.info("数据库客户端内容：" + list.toString());
		Assert.assertEquals("缓存客户端数量和数据库客户端数量需一致", list.size() - list1.size(), map.size());
	}

	/**
	 * 客户端缓存数据修改与还原
	 * 缓存数据量不发生变化
	 */
	@Test
	public void test4() {
		cacheRefreshUtil.clientCacheRefresh();
		Map<String, TlClient> map = clientCache.getValidDataMap();
		TlClient preClient = map.get("didu");
		String clientId = preClient.getClientId();
		String preKey2 = preClient.getClientKey2();
		logger.info("未修改前，客户端[{}]的client_key2为{}", clientId, preKey2);
		preClient.setClientKey2("client_key2");
		clientService.updateAllColumnById(preClient);
		cacheRefreshUtil.clientCacheRefresh();
		TlClient client = clientCache.getValidDataMap().get(clientId);
		Assert.assertNotEquals("修改后缓存client_key2发生变化", preKey2, client.getClientKey2());
		logger.info("修改后，客户端[{}]的client_key2为{}", clientId, client.getClientKey2());
		client.setClientKey2(preKey2);
		clientService.updateAllColumnById(client);
		cacheRefreshUtil.clientCacheRefresh();
		TlClient backClient = clientCache.getValidDataMap().get(clientId);
		logger.info("还原后，客户端[{}]的client_key2为{}", clientId, backClient.getClientKey2());
		Assert.assertEquals("修改前和还原后，client_key2未变", preKey2, backClient.getClientKey2());
	}

	/**
	 * 客户端数据状态的修改与还原
	 * 缓存数据量会发生变化
	 */
	@Test
	public void test5() {
		cacheRefreshUtil.clientCacheRefresh();
		Map<String, TlClient> map = clientCache.getValidDataMap();
		TlClient preClient = map.get("didu");
		String clientId = preClient.getClientId();
		Integer preStatus = preClient.getClientStatus();
		logger.info("未修改前，客户端[{}]的状态为{}", clientId, preStatus);
		logger.info("客户端缓存数量为{}", map.size());
		preClient.setClientStatus(0);
		clientService.updateAllColumnById(preClient);
		cacheRefreshUtil.clientCacheRefresh();
		TlClient client = clientCache.getValidDataMap().get(clientId);
		Assert.assertEquals("修改后客户端为无效，不缓存", null, client);
		logger.info("客户端缓存数量为{}", clientCache.getValidDataMap().size());
		preClient.setClientStatus(preStatus);
		clientService.insertOrUpdateAllColumn(preClient);
		cacheRefreshUtil.clientCacheRefresh();
		TlClient backClient = clientCache.getValidDataMap().get(clientId);
		logger.info("还原后，客户端[{}]的状态为{}", clientId, backClient.getClientStatus());
		logger.info("客户端缓存数量为{}", clientCache.getValidDataMap().size());
		Assert.assertEquals("修改前和还原后，状态未变", preStatus, backClient.getClientStatus());
	}

	/**
	 * 测试内容：客户端未获得token前失效，请求获取token
	 * 预期：发生异常，代码 -2
	 * HttpServletRequest对象采用mock方法创建，暂时没能设置ip等内容</br>
	 * 所以在测试的时候需要修改getNewMicroToken方法内的ip地址获取等内容
	 * @throws Exception
	 */
	@Test
	public void test6() {
		String clientStr = "test_server";
		String clientKeyStr = "test_server_key1";

		CodeMsgResult<String> result = tokenService.getToken(clientStr, clientKeyStr);
		logger.info("方法执行返回状态{},token为{}", result.getErrCode(), result.getResult());
		Assert.assertEquals("方法执行成功", "0", result.getErrCode());
		// 能正确获取token，说明客户端状态为1
		TlClient client = clientCache.getValidDataMap().get("test_server");
		Assert.assertEquals("客户端为有效状态", "1", client.getClientStatus() + "");

		// 修改客户端状态并刷新缓存，再次执行请求token，预期发生异常
		client.setClientStatus(0);
		clientService.updateAllColumnById(client);
		cacheRefreshUtil.clientCacheRefresh();

		ErrorCodeMsgException exp = null;
		try {
			tokenService.getToken(clientStr, clientKeyStr);
		} catch (ErrorCodeMsgException e) {
			exp = e;
		}
		Assert.assertEquals("客户端失效后，再次请求token发生异常", "-2", exp.getErrorCode());

		// 恢复初始数据，再次请求token，正常返回
		client.setClientStatus(1);
		clientService.updateAllColumnById(client);
		cacheRefreshUtil.clientCacheRefresh();

		CodeMsgResult<String> result1 = tokenService.getToken(clientStr, clientKeyStr);
		logger.info("方法执行返回状态{},token为{}", result.getErrCode(), result.getResult());
		Assert.assertEquals("方法执行成功", result.getErrCode(), result1.getErrCode());
	}

	/**
	 * 测试内容：客户端已经获得token后，客户端失效，请求需要token的路径</br>
	 * 预期：只要token未失效就可以正常访问</br>
	 * 暂时无法对带有shiro访问权限的方法进行测试</br>
	 * HttpServletRequest对象采用mock方法创建，暂时没能设置ip等内容</br>
	 * 所以在测试的时候需要修改getNewMicroToken方法内的ip地址获取等内容</br>
	 * @throws Exception
	 */
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void test7() throws Exception {
		String clientStr = "test_server";
		String clientKeyStr = "test_server_key1";

		TlClient preClient = clientCache.getValidDataMap().get(clientStr);
		logger.info("原客户端状态{}", preClient.getClientStatus());
		Assert.assertEquals("客户端为有效状态", "1", preClient.getClientStatus() + "");

		CodeMsgResult<String> token = tokenService.getToken(clientStr, clientKeyStr);

		// 改变客户端状态
		preClient.setClientStatus(0);
		clientService.updateAllColumnById(preClient);
		cacheRefreshUtil.clientCacheRefresh();
		TlClient client = clientCache.getValidDataMap().get(clientStr);
		logger.info("客户端失效后不缓存为{}", client);
		Assert.assertEquals("客户端失效后不缓存", null, client);

		// TODO 未检查token，所以过期或者客户单状态改变都不影响
		// 访问需要token的路径
		String url = "/auth/secret";
		RequestBuilder request = MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(AuthCommonConstants.AUTHORIZATION_HEADER, token.getResult());
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		CodeMsgResult<List<String>> secrets = JSONObject.parseObject(mvcResult.getResponse().getContentAsString(),
				CodeMsgResult.class);
		logger.info("请求成功，状态为{}", status);
		logger.info("获取密钥成功，返回状态为{}", secrets.getErrCode());
		Assert.assertEquals("请求成功，状态为{}", 200, status);
		Assert.assertEquals("获取密钥成功，返回状态为{}", "0", secrets.getErrCode());

		// 还原客户端数据
		preClient.setClientStatus(1);
		clientService.updateAllColumnById(preClient);
		cacheRefreshUtil.clientCacheRefresh();
	}
}
