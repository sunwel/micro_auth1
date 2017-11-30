package com.enlink.authclient;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.enlink.authcommon.model.CodeMsgResult;
import com.enlink.authcommon.model.MicroToken;
import com.enlink.authcommon.service.MicroTokenHelper;

/**
 * 微服务token的客户端服务
 * @author Timothy
 */
@Service
public class MicroTokenClientService {

	private static final Logger logger = LoggerFactory.getLogger(MicroTokenClientService.class);

	@Autowired
	private AuthClientProp authClientProp;
	@Autowired
	private ClientLoginServiceImpl clientLoginService;

	/** 微服务token对象 */
	private MicroToken microToken;
	/** 微服务token字符串 */
	private String microTokenStr;

	/**
	 * 服务初始化
	 */
	@PostConstruct
	public void init() {
		tokenRefresh();
	}

	/**
	 * 定时任务检查并刷新微服务token<br/>
	 * 默认每5分钟执行一次
	 */
	@Scheduled(initialDelayString = "#{@authClientProp.microTokenProp.checkRefreshInterval}", fixedDelayString = "#{@authClientProp.microTokenProp.checkRefreshInterval}")
	public void checkAndRefresh() {
		if (this.microToken != null) {
			// 如果当前时间 + 刷新时间提前量 >= 微服务Token到期时间，刷新微服务token
			if ((System.currentTimeMillis() + authClientProp.getMicroTokenProp().getRefreshBeforeExpire()) >= microToken
					.getExpireTime()) {
				this.tokenRefresh();
			}
		} else {
			// 立即无条件刷新
			this.tokenRefresh();
		}
	}

	private void tokenRefresh() {
		logger.info("微服务token刷新开始");
		boolean loginFlag = false;
		int tryCount = 0;

		while (!loginFlag && tryCount < authClientProp.getMicroTokenProp().getVisitTryMax()) {
			tryCount++;
			try {
				// 访问授权中心获得一个微服务token
				CodeMsgResult<String> codeMsgResult = clientLoginService.login();
				loginFlag = codeMsgResult.isSuccess();
				if (loginFlag) {
					String newTokenStr = codeMsgResult.getResult();
					MicroToken newToken = MicroTokenHelper.parseJwtPlayload(newTokenStr);
					this.microTokenStr = newTokenStr;
					this.microToken = newToken;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				loginFlag = false;
			}
		}

		logger.info("微服务token刷新结束，刷新{}", loginFlag ? "成功" : "失败");
	}

	public MicroToken getMicroToken() {
		return microToken;
	}

	public String getMicroTokenStr() {
		return microTokenStr;
	}
}
