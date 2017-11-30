package com.enlink.authcs;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.enlink.authclient.AuthClientProp;
import com.enlink.authclient.MicroTokenRestTemplate;
import com.enlink.authcommon.model.CodeMsgResult;
import com.enlink.authshiro.ISecretService;

/**
 * Created by Timothy on 2017/11/6.
 */
@Service
public class CsSecretService implements ISecretService {

	private static final Logger logger = LoggerFactory.getLogger(CsSecretService.class);

	@Autowired
	AuthClientProp authClientProp;
	@Autowired
	MicroTokenRestTemplate tokenRestTemplate;

	private List<String> secretList;

	@Override
	public List<String> getSecretList() {
		return secretList;
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		secretListRefresh();
	}

	/**
	 * 定时任务检查并且刷新密钥列表
	 * 默认每间隔30秒执行一次（注意是间隔）
	 */
	@Scheduled(initialDelayString = "#{@authClientProp.secretProp.refreshInterval}", fixedRateString = "#{@authClientProp.secretProp.refreshInterval}")
	public void checkAndRefresh() {
		secretListRefresh();
	}

	/**
	 * 刷新密钥列表
	 */
	private void secretListRefresh() {
		logger.info("微服务secret刷新开始");
		boolean secretFlag = false;
		int tryCount = 0;
		String fullUrl = authClientProp.getAuthCenterBaseUrl() + authClientProp.getSecretProp().getRelativeUrl();
		while (!secretFlag && tryCount < authClientProp.getSecretProp().getVisitTryMax()) {
			tryCount++;
			try {
				CodeMsgResult<List<String>> secretResult = tokenRestTemplate.getForObject(fullUrl, CodeMsgResult.class);
				secretFlag = secretResult.isSuccess();
				if (secretFlag) {
					this.secretList = secretResult.getResult();
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				secretFlag = false;
			}
		}
		logger.info("微服务secret刷新结束，刷新{}", secretFlag ? "成功" : "失败");
	}

}
