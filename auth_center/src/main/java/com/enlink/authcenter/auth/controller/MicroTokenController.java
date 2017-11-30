package com.enlink.authcenter.auth.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enlink.authcenter.auth.command.BaseLoginServiceCommand;
import com.enlink.authcenter.auth.model.ClientLoginReqContext;
import com.enlink.authcommon.constant.ResultStatusCodeEnum;
import com.enlink.authcommon.exception.ErrorCodeMsgException;
import com.enlink.authcommon.model.CodeMsgResult;
import com.enlink.authcommon.model.login.BaseSignedLogin;

/**
 * 微服务Token相关API
 * @author Timothy
 */
@RestController
public class MicroTokenController {

	private static final Logger logger = LoggerFactory.getLogger(MicroTokenController.class);

	/**
	 * 登录服务命令集合
	 */
	@Resource(name = "loginServiceCommandList")
	private List<BaseLoginServiceCommand> loginServiceCommandList;
	/**
	 * 登录服务责任链
	 */
	private ChainBase loginServiceChain;

	@PostConstruct
	public void init() {
		// 登录服务责任链初始化
		this.loginServiceChain = new ChainBase(loginServiceCommandList);
	}

	/**
	 * 客户端向授权中心发起登录请求以获取新的微服务Token
	 * @param loginReq 登录请求基本信息
	 * @param loginReqMap 登录请求参数集
	 * @param request HttpServletRequest
	 * @return 微服务Token字符串
	 */
	@RequestMapping(value = "/auth/token", method = RequestMethod.GET)
	public CodeMsgResult<String> getNewMicroToken(BaseSignedLogin loginReq,
			@RequestParam Map<String, String> loginReqMap, HttpServletRequest request) {
		logger.info("授权中心接受token请求，开始处理.....");
		logger.info("请求参数：{};loginReqMap={}", loginReq.toString(), loginReqMap);
		// 验证clientLoginReq
		validateLoginReq(loginReq);
		// 获取请求者的IP地址
		String clientIp = getRemoteIp(request);
		// 构造登录请求context
		ClientLoginReqContext context = new ClientLoginReqContext(loginReq, loginReqMap, clientIp);
		// 责任链方式执行登录
		chainLoginExecute(context);
		// 获取微服务Token字符串
		String microTokenStr = context.getMicroTokenStr();
		if (StringUtils.isEmpty(microTokenStr)) {
			return CodeMsgResult.failureResult(ResultStatusCodeEnum.ALGID_INVALID);
		}
		logger.info("token请求通过，授权中心返回token={}", microTokenStr);
		logger.info("授权中心接受token请求，处理完成.....");
		// 包装返回响应结果
		return CodeMsgResult.sucessResult(microTokenStr);
	}

	private void validateLoginReq(BaseSignedLogin loginReq) {
		if (StringUtils.isEmpty(loginReq.getClientId())) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.CLIENTID_INVALID);
		} else if (StringUtils.isEmpty(loginReq.getClientSign())) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.CLIENTSIGN_INVALID);
		} else if (loginReq.getAlgId() < 1) {
			throw new ErrorCodeMsgException(ResultStatusCodeEnum.ALGID_INVALID);
		}
	}

	/**
	 * 获取请求者的IP地址
	 * @param request HttpServletRequest
	 * @return 请求者的IP地址
	 */
	private String getRemoteIp(HttpServletRequest request) {
		String remoteIp = "";
		if (request != null) {
			remoteIp = request.getHeader("X-FORWARDED-FOR");
			if (remoteIp == null || "".equals(remoteIp)) {
				remoteIp = request.getRemoteAddr();
			}
		}
		return remoteIp;
	}

	/**
	 * 链式的登录验证
	 * @param context 登录请求上下文
	 */
	private void chainLoginExecute(ClientLoginReqContext context) {
		try {
			loginServiceChain.execute(context);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			if (e instanceof ErrorCodeMsgException) {
				throw (ErrorCodeMsgException) e;
			} else {
				throw new ErrorCodeMsgException(ResultStatusCodeEnum.SYSTEM_ERR);
			}
		}
	}

}
