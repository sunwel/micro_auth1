package com.enlink.authserver;

import org.apache.commons.lang3.RandomUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.enlink.authcommon.model.CodeMsgResult;

/**
 * server端controller测试类
 */
@RestController
public class ServerControllerTest {

	/**
	 * 一个不需要任何权限的访问接口
	 * @return CodeMsgResult
	 */
	@RequestMapping(value = "/open/hello", method = RequestMethod.GET)
	public CodeMsgResult<String> hello4EveryOne() {
		return CodeMsgResult.sucessResult("hello!");
	}

	/**
	 * 获取随机数接口<br/>
	 * 此接口没有设置任何权限，只要微服务Token自身有效即可访问
	 * @return CodeMsgResult
	 */
	@RequestMapping(value = "/test/getRandom", method = RequestMethod.GET)
	public CodeMsgResult<Integer> getRandom() {
		return CodeMsgResult.sucessResult(getRandomInt());
	}

	/**
	 * 获取随机数，需要权限priv1<br/>
	 * @return CodeMsgResult
	 */
	@RequestMapping(value = "/test/getRandomPriv1", method = RequestMethod.GET)
	@RequiresPermissions("priv1")
	public CodeMsgResult<Integer> getRandomPriv1() {
		return CodeMsgResult.sucessResult(getRandomInt());
	}

	/**
	 * 获取随机数，需要权限priv2<br/>
	 * @return CodeMsgResult
	 */
	@RequestMapping(value = "/test/getRandomPriv2", method = RequestMethod.GET)
	@RequiresPermissions("priv2")
	public CodeMsgResult<Integer> getRandomPriv2() {
		return CodeMsgResult.sucessResult(getRandomInt());
	}

	/**
	 * 获取随机数，需要权限priv3；如果testException为true则抛出异常<br/>
	 * @param testException 是否测试异常抛出
	 * @return CodeMsgResult
	 * @throws Exception
	 */
	@RequestMapping(value = "/test/getRandomPriv3", method = RequestMethod.GET)
	@RequiresPermissions("priv3")
	public CodeMsgResult<Integer> getRandomPriv3(boolean testException) throws Exception {
		if (testException) {
			throw new Exception("test for exception");
		}
		return CodeMsgResult.sucessResult(getRandomInt());
	}

	private int getRandomInt() {
		return RandomUtils.nextInt();
	}
}
