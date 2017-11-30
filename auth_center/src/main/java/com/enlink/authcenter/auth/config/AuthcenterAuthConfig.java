package com.enlink.authcenter.auth.config;

import java.util.ArrayList;
import java.util.List;

import com.enlink.authcenter.auth.command.Alg1LoginServiceCommand;
import com.enlink.authcenter.auth.command.BaseLoginServiceCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 授权中心的授权相关配置
 * @author Timothy
 */
@Configuration
public class AuthcenterAuthConfig {

	@Autowired
	private Alg1LoginServiceCommand algorithm1LoginServiceCommand;

	/**
	 * 登录服务命令集合
	 * @return 登录服务命令集合
	 */
	@Bean(name = "loginServiceCommandList")
	public List<BaseLoginServiceCommand> loginServiceCommandList() {
		List<BaseLoginServiceCommand> commandList = new ArrayList<>();
		commandList.add(algorithm1LoginServiceCommand);
		return commandList;
	}
}
