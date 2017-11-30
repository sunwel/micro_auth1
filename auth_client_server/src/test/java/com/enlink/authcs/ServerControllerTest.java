package com.enlink.authcs;

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
	 * @return
	 */
	@RequestMapping(value = "/test/getRandomInt", method = RequestMethod.GET)
	public CodeMsgResult<Integer> getRandomInt() {
		int randomInt = RandomUtils.nextInt();
		return CodeMsgResult.sucessResult(randomInt);
	}

	/**
	 * 获取随机长整数<br/>
	 * 此地址需要权限priv1
	 * @return
	 */
	@RequestMapping(value = "/test/getRandomLong", method = RequestMethod.GET)
	@RequiresPermissions("priv1")
	public CodeMsgResult<Long> getRandomLong() {
		long randomLong = RandomUtils.nextLong();
		return CodeMsgResult.sucessResult(randomLong);
	}

	@RequestMapping(value = "/test/getRandomPriv3", method = RequestMethod.GET)
	@RequiresPermissions("priv3")
	public CodeMsgResult<Integer> getRandomPriv3(boolean testException) throws Exception {
		if (testException) {
			throw new Exception("test for exception");
		}
		return CodeMsgResult.sucessResult(0);
	}
}
