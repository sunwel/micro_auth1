package com.enlink.authcenter.auth.service.cache;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 持久层数据缓存基础类
 * @author Timothy
 */
public abstract class BaseCache<K, V> {

	private static final Logger logger = LoggerFactory.getLogger(BaseCache.class);

	private AtomicBoolean atomicRefresh = new AtomicBoolean(false);

	/** 有效数据MAP */
	private Map<K, V> validDataByIdMap = new LinkedHashMap<>();

	/**
	 * 获取缓存的名称，主要用于日志输出
	 * @return 缓存的名称
	 */
	protected abstract String getCacheName();

	/**
	 * 从数据库中获取全部有效数据
	 * @return 全部有效数据
	 */
	protected abstract List<V> getAllValidDataFromDb();

	/**
	 * 获取数据的主键ID
	 * @param data 数据
	 * @return 主键ID
	 */
	protected abstract K getDataId(V data);

	/**
	 * 缓存初始化<br/>
	 */
	@PostConstruct
	public void init() {
		this.refresh();
		this.checkRefresh();
	}

	/**
	 * 接收外部缓存刷新请求
	 */
	public void refresh() {
		logger.info("收到{}缓存刷新请求", getCacheName());
		this.atomicRefresh.set(true);
		logger.info("结束{}缓存刷新请求", getCacheName());
	}

	/**
	 * 检查是否需要刷新缓存，如果需要，重新获取数据并刷新缓存
	 */
	public synchronized void checkRefresh() {
		// 取出当前的缓存刷新标志，同时将刷新标志设置为false
		boolean isRefresh = atomicRefresh.getAndSet(false);
		if (isRefresh) {
			logger.info("{} 缓存刷新开始", getCacheName());
			// 数据库中获取全部数据
			List<V> dataList = getAllValidDataFromDb();
			// 新全部有效数据Map初始化
			Map<K, V> newValidMap = new LinkedHashMap<>();
			for (V data : dataList) {
				newValidMap.put(getDataId(data), data);
			}
			this.validDataByIdMap = newValidMap;
			logger.info("{} 缓存刷新结束", getCacheName());
		}
	}

	/**
	 * 取得所有有效状态的数据对象MAP
	 * @return 所有有效状态的数据对象MAP
	 */
	public Map<K, V> getValidDataMap() {
		return this.validDataByIdMap;
	}
}
