package com.enlink.authserver;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enlink.authcommon.model.MicroToken;
import com.enlink.authcommon.service.MicroTokenHelper;
import com.enlink.authserver.AuthServerProp;
import com.enlink.authshiro.IJwtUserRepository;
import com.enlink.authshiro.ISecretService;
import com.enlink.authshiro.JwtRepoAuthenticationToken;

/**
 * 服务端的用户持久层服务实现
 * @author Timothy
 */
@Component
public class ServerUserRepository implements IJwtUserRepository {

	@Autowired
	private AuthServerProp authServerProp;
	@Autowired
	private ISecretService secretService;

	@Override
	public JwtRepoAuthenticationToken findByJwt(String jwt) {
		MicroToken microToken = MicroTokenHelper.parseJwt(jwt, secretService.getSecretList());
		List<String> privilegeList = microToken.getPrivilegeMap().get(authServerProp.getServerName());
		Set<String> privilegeSet = getPrivilegeSet(privilegeList);
		JwtRepoAuthenticationToken authenticationUser = new JwtRepoAuthenticationToken(privilegeSet,
				microToken.getClientId(), jwt);
		return authenticationUser;
	}

	private Set<String> getPrivilegeSet(List<String> privilegeList) {
		if (privilegeList == null) {
			return new HashSet<>();
		} else {
			return new HashSet<>(privilegeList);
		}
	}
}
