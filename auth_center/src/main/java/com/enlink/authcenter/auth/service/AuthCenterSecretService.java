package com.enlink.authcenter.auth.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enlink.authcenter.auth.service.cache.SecretCache;
import com.enlink.authshiro.ISecretService;

/**
 * 授权中心的密钥服务实现类
 * @author Timothy
 */
@Service
public class AuthCenterSecretService implements ISecretService {

	@Autowired
	private SecretCache secretCache;

	@Override
	public List<String> getSecretList() {
		return secretCache.getValidDataMap().values().stream().map(r -> r.getSecret()).collect(Collectors.toList());
	}
}
