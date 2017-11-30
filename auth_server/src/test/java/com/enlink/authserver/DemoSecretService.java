package com.enlink.authserver;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.enlink.authshiro.ISecretService;

/**
 * Server端密钥服务的Demo实现
 */
@Service
public class DemoSecretService implements ISecretService {

	private static final String secret = "vbID6jvbolj1kNMaW6uv3Vryazw2V^Qx";

	@Override
	public List<String> getSecretList() {
		return Arrays.asList(secret);
	}
}
