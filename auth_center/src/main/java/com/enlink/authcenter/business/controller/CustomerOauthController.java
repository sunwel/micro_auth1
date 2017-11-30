package com.enlink.authcenter.business.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.enlink.authcenter.business.service.CustomerOauthService;
import com.enlink.authcommon.model.CodeMsgResult;

@Controller
@RequestMapping("customerOauth")
public class CustomerOauthController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerOauthController.class);
	@Autowired
	private CustomerOauthService customerOauthService;

	/**
	 * @param code 微信返回的code
	 */
	@GetMapping("oauth")
	@RequiresPermissions("weixin-oauth")
	public @ResponseBody CodeMsgResult<String> oauth(String code, String state,
			@RequestParam(required = false, defaultValue = "0") int subscribe, String resourceId, String redirect_url)
			throws Exception {
		logger.info("oauth请求参数：resourceId={};code={};state={};subscribe={};redirect_url={}", resourceId, code, state,
				subscribe, redirect_url);
		return CodeMsgResult.sucessResult(customerOauthService.Oauth(resourceId, code, state, redirect_url, subscribe));

	}

	@GetMapping("getCliToken")
	@RequiresPermissions("weixin-oauth")
	public @ResponseBody CodeMsgResult<Map<Object, Object>> getCliToken(String resourceId) throws Exception {
		logger.info("getCliToken请求参数：resourceId={}", resourceId);
		return CodeMsgResult.sucessResult(this.customerOauthService.getCliToken(resourceId, false));
	}

	@GetMapping("getCliTicket")
	@RequiresPermissions("weixin-oauth")
	public @ResponseBody CodeMsgResult<Map<Object, Object>> getCliTicket(String resourceId) throws Exception {
		logger.info("getCliTicket请求参数：resourceId={}", resourceId);
		return CodeMsgResult.sucessResult(this.customerOauthService.getCliTicket(resourceId, false));
	}

	@GetMapping("getCliJSTicket")
	@RequiresPermissions("weixin-oauth")
	public @ResponseBody CodeMsgResult<Map<Object, Object>> getCliJSTicket(String resourceId, String url)
			throws Exception {
		logger.info("getCliJSTicket请求参数：resourceId={};url={}", resourceId, url);
		return CodeMsgResult.sucessResult(this.customerOauthService.getCliJSTicket(resourceId, url));
	}

}
