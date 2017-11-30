package com.enlink.authcenter.auth.service.cache;

import java.util.ArrayList;
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
import com.enlink.auth.dao.entity.TlSecret;
import com.enlink.auth.dao.service.ITlSecretService;
import com.enlink.authcenter.AuthCenterApplication;
import com.enlink.authcenter.CacheRefreshUtil;
import com.enlink.authcenter.TokenService;
import com.enlink.authcommon.constant.AuthCommonConstants;
import com.enlink.authcommon.model.CodeMsgResult;

/**
 * Created by someone on 2017/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AuthCenterApplication.class)
@ActiveProfiles("h2")
public class SecretCacheTest {

	private static final Logger logger = LoggerFactory.getLogger(SecretCacheTest.class);

	@Autowired
	SecretCache secretCache;
	@Autowired
	ITlSecretService tlSecretService;
	@Autowired
	TokenService tokenService;
	@Autowired
	CacheRefreshUtil cacheRefreshUtil;
	// @Autowired
	private MockMvc mockMvc;
	@Autowired
	private WebApplicationContext wac;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	/**
	 * 测试内容：使用密钥生成token，然后将密钥设为无效，利用已生成的token访问需要token请求的路径</br>
	 * 预期：token未过期前，可以正常访问</br>
	 * 生成token时使用有效密钥，解析token时会使用所有密钥逐一解析，所以只要token未过期就可以正常访问</br>
	 * 暂时无法测试带有shiro权限
	 */
	@Test
	public void test1() throws Exception {
		String clientStr = "test_server";
		String clientKeyStr = "test_server_key1";

		// 为确保密钥的准确性，数据库只保存两条密钥，状态分别为1和0
		TlSecret bakSecret = secretCache.getValidDataMap().values().iterator().next();
		tlSecretService.deleteById(bakSecret.getSecretId());
		cacheRefreshUtil.secretCacheRefresh();

		// 授权中心获取密钥的方法，生成token的密钥
		List<TlSecret> secretList = tlSecretService.selectList(new EntityWrapper<>());
		logger.info("授权中心密钥数量为{}", secretList.size());
		Map<Integer, TlSecret> map = secretCache.getValidDataMap();
		logger.info("授权中心缓存密钥数量为{}", map.size());
		TlSecret preSecret = secretCache.getValidDataMap().values().iterator().next();
		logger.info("授权中心生成token所使用密钥为{}", preSecret.getSecret());
		logger.info("密钥状态为{}", preSecret.getStatus());
		Assert.assertEquals("密钥状态为有效", "1", preSecret.getStatus() + "");
		CodeMsgResult<String> token = tokenService.getToken(clientStr, clientKeyStr);
		Assert.assertEquals("token生成成功", "0", token.getErrCode());
		// 修改密钥状态
		preSecret.setStatus(0);
		tlSecretService.updateAllColumnById(preSecret);
		cacheRefreshUtil.secretCacheRefresh();
		Assert.assertEquals("缓存密钥减少一个", map.size() - 1, secretCache.getValidDataMap().size());
		Assert.assertEquals("缓存密钥个数应为零", 0, secretCache.getValidDataMap().size());

		// TODO 没有经过token校验，授权中心没有缓存无效的密钥
		// 访问需要token的路径(token未过期时)
		String url = "/auth/secret";
		RequestBuilder request = MockMvcRequestBuilders.get(url).contentType(MediaType.APPLICATION_JSON_UTF8)
				.header(AuthCommonConstants.AUTHORIZATION_HEADER, token.getResult());
		MvcResult mvcResult = mockMvc.perform(request).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("请求成功，状态为{}", status);
		CodeMsgResult<List<String>> secrets = JSONObject.parseObject(mvcResult.getResponse().getContentAsString(),
				CodeMsgResult.class);
		Assert.assertEquals("密钥获取成功", "0", secrets.getErrCode());

		// 再次获取token，如果能成功获取新的token，说明密钥可以无缝切换
		Wrapper<TlSecret> wrapper = new EntityWrapper<>();
		wrapper.ne(TlSecret.SECRET_ID, preSecret.getSecretId());
		TlSecret secret = tlSecretService.selectList(wrapper).get(0);
		secret.setStatus(1);
		tlSecretService.updateAllColumnById(secret);
		cacheRefreshUtil.secretCacheRefresh();
		Assert.assertEquals("缓存中有一条密钥信息", 1, secretCache.getValidDataMap().size());

		CodeMsgResult<String> newToken = tokenService.getToken(clientStr, clientKeyStr);
		Assert.assertEquals("token获取成功", "0", newToken.getErrCode());

		// 还原数据
		List<TlSecret> list = new ArrayList<>();
		preSecret.setStatus(1);
		list.add(preSecret);
		secret.setStatus(0);
		list.add(secret);
		tlSecretService.updateAllColumnBatchById(list);
		tlSecretService.insert(bakSecret);
		cacheRefreshUtil.secretCacheRefresh();
		Assert.assertEquals("缓存中有两条有效密钥", 2, secretCache.getValidDataMap().size());
		Wrapper<TlSecret> secretWrapper = new EntityWrapper<>();
		secretWrapper.eq(TlSecret.STATUS, 0);
		List<TlSecret> list0 = tlSecretService.selectList(secretWrapper);
		Assert.assertEquals("数据库中有一条无效密钥数据", 1, list0.size());
	}

	/**
	 * 测试内容：密钥无缝更换
	 * 预期：密钥正常被更换
	 * 密钥获取方法为有效倒叙，所以只要新增一条密钥，确认是否使用该密钥生成新的token即可
	 */
	@Test
	public void test2() throws Exception {
		// 授权中心生成token时获取密钥的方法
		TlSecret preSecret = secretCache.getValidDataMap().values().iterator().next();
		logger.info("密钥id为{},密钥为{}", preSecret.getSecretId(), preSecret.getSecret());
		TlSecret secret = new TlSecret();
		secret.setSecret("1234567890");
		secret.setStatus(1);
		tlSecretService.insert(secret);
		cacheRefreshUtil.secretCacheRefresh();
		TlSecret newSecret = secretCache.getValidDataMap().values().iterator().next();
		logger.info("新密钥id为{},新密钥为{}", newSecret.getSecretId(), newSecret.getSecret());
		Assert.assertNotSame("新增密钥id大于原密钥id", newSecret.getSecretId(), preSecret.getSecretId());
		Assert.assertEquals("密钥为新密钥", "1234567890", newSecret.getSecret());
	}

}
