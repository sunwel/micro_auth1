package com.enlink.authcenter.auth.service.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.enlink.auth.dao.entity.TlMicroservice;
import com.enlink.auth.dao.service.ITlMicroserviceService;

/**
 * 微服务信息缓存
 * @author Timothy
 */
@Service
public class MicroserviceCache extends BaseCache<Integer, TlMicroservice> {

	@Autowired
	private ITlMicroserviceService microserviceService;

	@Override
	protected String getCacheName() {
		return "微服务信息";
	}

	@Override
	protected List<TlMicroservice> getAllValidDataFromDb() {
		Wrapper<TlMicroservice> wrapper = new EntityWrapper<>();
		wrapper.eq(TlMicroservice.SERVICE_STATUS, 1);
		return microserviceService.selectList(wrapper);
	}

	@Override
	protected Integer getDataId(TlMicroservice data) {
		return data.getServiceId();
	}
}
