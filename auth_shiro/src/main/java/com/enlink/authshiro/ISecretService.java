package com.enlink.authshiro;

import java.util.List;

/**
 * 密钥服务接口
 * @author Timothy
 */
public interface ISecretService {

	/**
	 * 取得密钥集合
	 * @return 密钥集合
	 */
	List<String> getSecretList();
}
