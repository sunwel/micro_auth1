package com.enlink.authcenter.auth.service.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enlink.auth.dao.entity.TlClientFunc;
import com.enlink.auth.dao.service.ITlClientFuncService;

/**
 * 客户端功能权限缓存
 * @author Timothy
 */
@Service
public class ClientFuncCache extends BaseCache<Integer, TlClientFunc> {

	@Autowired
	private ITlClientFuncService clientFuncService;

	@Override
	protected String getCacheName() {
		return "客户端功能权限";
	}

	@Override
	protected List<TlClientFunc> getAllValidDataFromDb() {
		return clientFuncService.selectList(null);
	}

	@Override
	protected Integer getDataId(TlClientFunc data) {
		return data.getCfId();
	}
}
