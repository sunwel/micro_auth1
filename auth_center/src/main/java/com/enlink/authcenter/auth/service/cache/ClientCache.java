package com.enlink.authcenter.auth.service.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.enlink.auth.dao.entity.TlClient;
import com.enlink.auth.dao.service.ITlClientService;

/**
 * 客户端信息缓存
 * @author Timothy
 */
@Service
public class ClientCache extends BaseCache<String, TlClient> {

	@Autowired
	ITlClientService clientService;

	@Override
	protected String getCacheName() {
		return "客户端信息";
	}

	@Override
	protected List<TlClient> getAllValidDataFromDb() {
		Wrapper<TlClient> wrapper = new EntityWrapper<>();
		wrapper.eq(TlClient.CLIENT_STATUS, 1);
		return clientService.selectList(wrapper);
	}

	@Override
	protected String getDataId(TlClient data) {
		return data.getClientId();
	}

}
