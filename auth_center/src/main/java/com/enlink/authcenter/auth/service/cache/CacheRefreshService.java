package com.enlink.authcenter.auth.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 持久层数据缓存刷新服务
 * @author Timothy
 */
@Service
public class CacheRefreshService {

	@Autowired
	private ClientCache clientCache;
	@Autowired
	private MicroserviceCache microserviceCache;
	@Autowired
	private MicroserviceFuncCache microserviceFuncCache;
	@Autowired
	private ClientFuncCache clientFuncCache;
	@Autowired
	private SecretCache secretCache;

	/**
	 * 定时请求刷新缓存<br/>
	 * fixedRateString: 每隔多长时间执行这个任务<br/>
	 * initialDelayString: 首次执行这个任务的延时时间
	 */
	@Scheduled(fixedRateString = "${authcenter.auth.cacheRefreshFixedRate:60000}", initialDelayString = "${authcenter.auth.cacheRefreshFixedRate:60000}")
	public void cacheScheduleRefresh() {
		clientCache.refresh();
		microserviceCache.refresh();
		microserviceFuncCache.refresh();
		clientFuncCache.refresh();
		secretCache.refresh();
	}

	/**
	 * 定时检查并执行相应的缓存刷新<br/>
	 * fixedRateString: 每隔多长时间执行这个任务<br/>
	 * initialDelayString: 首次执行这个任务的延时时间
	 */
	@Scheduled(fixedRateString = "${authcenter.auth.cacheRefreshCheckFixedRate:5000}", initialDelayString = "${authcenter.auth.cacheRefreshCheckFixedRate:5000}")
	public void cacheScheduleRefreshCheck() {
		clientCache.checkRefresh();
		microserviceCache.checkRefresh();
		microserviceFuncCache.checkRefresh();
		clientFuncCache.checkRefresh();
		secretCache.checkRefresh();
	}

}
