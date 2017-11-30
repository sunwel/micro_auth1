package com.enlink.authserver;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import com.enlink.authshiro.JwtAuthenticationFilter;
import com.enlink.authshiro.JwtRealm;
import org.springframework.context.annotation.Configuration;

/**
 * server端shiro相关配置基础类
 * @author Timothy
 */
@Configuration
public class ServerShiroConfig {

	private static final Logger logger = LoggerFactory.getLogger(ServerShiroConfig.class);

	/** JWT权限检查filter的key name */
	protected static final String KEY_JWT_AUTHENTICATION_FILTER = "jwtAuthenticationFilter";

	@Bean
	public Authorizer authorizer(JwtRealm jwtRealm) {
		return jwtRealm;
	}

	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
			JwtAuthenticationFilter jwtAuthenticationFilter, AuthServerProp authServerProp) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		// 设置securityManager
		shiroFilter.setSecurityManager(securityManager);
		// shiroFilter.setUnauthorizedUrl("/");

		// 设置权限检查过滤器
		Map<String, Filter> filters = new LinkedHashMap();
		filters.put(KEY_JWT_AUTHENTICATION_FILTER, jwtAuthenticationFilter);
		shiroFilter.setFilters(filters);
		// 设置需要进行权限检查的路径
		Map<String, String> filterMap = new LinkedHashMap<>();
		setNeedMicroTokenCheckPaths(authServerProp, filterMap);
		shiroFilter.setFilterChainDefinitionMap(filterMap);
		return shiroFilter;
	}

	@Bean
	protected SessionManager sessionManager() {
		logger.info("ShiroConfig.getDefaultSessionManager()");
		DefaultSessionManager manager = new DefaultSessionManager();
		manager.setSessionValidationSchedulerEnabled(false);
		return manager;
	}

	private void setNeedMicroTokenCheckPaths(AuthServerProp authServerProp, Map<String, String> filterMap) {
		List<String> needMicroTokenCheckPaths = authServerProp.getNeedMicroTokenCheckPaths();
		if (needMicroTokenCheckPaths != null) {
			needMicroTokenCheckPaths.stream().forEach(path -> filterMap.put(path, KEY_JWT_AUTHENTICATION_FILTER));
		}
	}
}
