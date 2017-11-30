package com.enlink.authcenter.auth.service.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.enlink.auth.dao.entity.TlMicroserviceFunc;
import com.enlink.auth.dao.service.ITlMicroserviceFuncService;

/**
 * 微服务功能缓存
 */
@Service
public class MicroserviceFuncCache extends BaseCache<Integer, TlMicroserviceFunc> {

	@Autowired
	private ITlMicroserviceFuncService microserviceFuncService;

	@Override
	protected String getCacheName() {
		return "微服务功能";
	}

	@Override
	protected List<TlMicroserviceFunc> getAllValidDataFromDb() {
		Wrapper<TlMicroserviceFunc> wrapper = new EntityWrapper<>();
		wrapper.eq(TlMicroserviceFunc.FUNC_STATUS, 1);
		return microserviceFuncService.selectList(wrapper);
	}

	@Override
	protected Integer getDataId(TlMicroserviceFunc data) {
		return data.getFuncId();
	}
}
