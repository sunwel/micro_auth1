package com.enlink.authcenter.auth.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enlink.authcommon.model.CodeMsgResult;
import com.enlink.authshiro.ISecretService;

/**
 * 密钥服务Controller
 * @author Timothy
 */
@RestController
public class SecretController {

	private final static Logger logger = LoggerFactory.getLogger(SecretController.class);
	@Autowired
	ISecretService secretService;

	/**
	 * 获取有效密钥集合<br/>
	 * 需要密钥功能权限，secret
	 * @return 有效密钥集合
	 */
	@GetMapping(path = "/auth/secret")
	@RequiresPermissions("secret")
	public CodeMsgResult<List<String>> getSecretList() {
		logger.info("授权中心接受密钥请求，开始处理.....");
		List<String> list = secretService.getSecretList();
		logger.info("密钥请求通过，授权中心返回有效密钥列表，list.size={}", list.size());
		logger.info("授权中心密钥请求通过，结束处理.....");
		return CodeMsgResult.sucessResult(list);
	}
}
