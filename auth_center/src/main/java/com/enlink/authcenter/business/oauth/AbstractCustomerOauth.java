package com.enlink.authcenter.business.oauth;

import java.net.URLEncoder;
import java.util.Map;

public abstract class AbstractCustomerOauth {


	public String urlParam(String url, Map<String, String> params, String resourceId) throws Exception {
//		if (enc == 1) {
//			return this.paramEncode(url, params, client_id);
//		} else {
		return this.paramUnEncode(url, params);
//		}
	}

	private String paramUnEncode(String url, Map<String, String> params) throws Exception {
		if (url.contains("?")) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				url += "&" + entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
			}
		} else {
			url += "?";
			for (Map.Entry<String, String> entry : params.entrySet()) {
				url += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&";
			}
			url = url.substring(0, url.length() - 1);
		}
		return url;
	}

	public abstract String getAccessToken(String code, Map<String,String> map) throws Exception;

	public abstract String getInfoByClient(String clientToken, String openid) throws Exception;

	public abstract String getInfoByUserToken(String user_access_token, Map<String,String> map, String openId) throws Exception;

	public abstract Map<Object, Object> getClientToken(Map<String,String> map) throws Exception;

	public abstract Map<Object, Object> getClientToken(Map<String,String> map, boolean needRefresh) throws Exception;

	public abstract Map<Object, Object> getClientTicket(Map<String,String> map, Map<Object, Object> tokenMap, boolean needRefresh) throws Exception;

//	public abstract String oauth(String code, Map<String,String> map, String state, String url) throws Exception;

//	public abstract String parseJson(CustomerUserInfo customerUserInfo) throws Exception;

}
