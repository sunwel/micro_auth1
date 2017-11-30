package com.enlink.authcenter.business.service;

import java.util.Map;

public interface CustomerOauthService {
	public String Oauth(String resourceId, String code, String state, String url, int subscribe) throws Exception;

	public Map<Object, Object> getCliToken(String resourceId, boolean needRefresh) throws Exception;

	public Map<Object, Object> getCliTicket(String resourceId, boolean needRefresh) throws Exception;

	public Map<Object, Object> getCliJSTicket(String resourceId, String url) throws Exception;

}
