package com.enlink.authcenter.auth.service.cache;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.enlink.auth.dao.entity.TlSecret;
import com.enlink.auth.dao.service.ITlSecretService;

/**
 * 私钥表缓存
 * @author Timothy
 */
@Service
public class SecretCache extends BaseCache<Integer, TlSecret> {

	@Autowired
	private ITlSecretService secretService;

	@Override
	protected String getCacheName() {
		return "密钥缓存";
	}

	@Override
	protected List<TlSecret> getAllValidDataFromDb() {
		Wrapper<TlSecret> wrapper = new EntityWrapper<>();
		wrapper.eq(TlSecret.STATUS, 1);
		wrapper.orderBy(TlSecret.SECRET_ID);
		return secretService.selectList(wrapper);
	}

	@Override
	protected Integer getDataId(TlSecret data) {
		return data.getSecretId();
	}
}
