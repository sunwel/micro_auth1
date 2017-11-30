package com.enlink.authcenter.auth.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enlink.auth.dao.entity.TlClientFunc;
import com.enlink.auth.dao.entity.TlMicroservice;
import com.enlink.auth.dao.entity.TlMicroserviceFunc;
import com.enlink.authcenter.auth.service.cache.ClientFuncCache;
import com.enlink.authcenter.auth.service.cache.MicroserviceCache;
import com.enlink.authcenter.auth.service.cache.MicroserviceFuncCache;

/**
 * 客户端权限服务
 * @author Timothy
 */
@Service
public class ClientPrivilegeService {

	@Autowired
	private ClientFuncCache clientFuncCache;
	@Autowired
	private MicroserviceFuncCache microserviceFuncCache;
	@Autowired
	private MicroserviceCache microserviceCache;

	public Map<String, List<String>> getClientPrivilege(String clientId, boolean isAdmin) {
		// 取得有效的Map<微服务ID, 微服务>
		Map<Integer, TlMicroservice> microserviceByIdMap = microserviceCache.getValidDataMap();

		// 取得clientId的所有功能ID
		Set<Integer> funcIdSet = getFuncIdSet(clientId, isAdmin);

		// 取得该客户端的功能权限
		Map<String, List<String>> clientPrivilege = microserviceFuncCache.getValidDataMap().values().stream()
				// 过滤掉功能ID不符合要求的数据
				.filter(microFunc -> funcIdSet.contains(microFunc.getFuncId()))
				// 过滤掉对应的微服务状态无效的数据
				.filter(microFunc -> microserviceByIdMap.containsKey(microFunc.getServiceId()))
				// 输出 Map<微服务功能ID, List<微服务功能名>>
				.collect(
						// 分组构成Map
						Collectors.groupingBy(
								// key: 微服务名称
								microFunc -> microserviceByIdMap.get(microFunc.getServiceId()).getServiceName(),
								// value: 该微服务的功能名称集合
								Collectors.mapping(TlMicroserviceFunc::getFuncName, Collectors.toList())));

		// 出于安全考虑，再过滤一次，过滤掉微服务名为空的数据（可能是无用功，先保留）
		Map<String, List<String>> realClientPrivilege = clientPrivilege.keySet().stream().filter(k -> k != null)
				.collect(Collectors.toMap(k -> k, k -> clientPrivilege.get(k)));
		return realClientPrivilege;
	}

	/**
	 * 取得clientId的所有功能ID，如果是管理员则拥有所有权限
	 * @param clientId 客户端ID
	 * @param isAdmin 是否为管理员
	 * @return clientId的所有功能ID
	 */
	private Set<Integer> getFuncIdSet(String clientId, boolean isAdmin) {
		if (isAdmin) {
			// 是admin, 取得所有功能ID
			Set<Integer> funcIdSet = clientFuncCache.getValidDataMap().values().stream()
					// 映射为功能ID
					.map(TlClientFunc::getFuncId)
					// 输出Set<功能ID>
					.collect(Collectors.toSet());
			return funcIdSet;
		} else {
			// 取得clientId下的所有功能ID
			Set<Integer> funcIdSet = clientFuncCache.getValidDataMap().values().stream()
					// 根据clientId进行过滤
					.filter(g -> g.getClientId().equals(clientId))
					// 映射为功能ID
					.map(TlClientFunc::getFuncId)
					// 输出Set<功能ID>
					.collect(Collectors.toSet());
			return funcIdSet;
		}
	}
}
