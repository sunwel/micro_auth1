package com.enlink.authcenter.business.oauth;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service("qq")
public class QQOauth extends AbstractCustomerOauth {


	@Override
	public String getAccessToken(String code, Map<String,String> cr) throws Exception {
		return null;
	}

	@Override
	public String getInfoByClient(String clientToken, String openid) throws Exception {
		return null;
	}

	@Override
	public String getInfoByUserToken(String user_access_token, Map<String,String> cr, String openId) throws Exception {
		return null;
	}

	@Override
	public Map<Object, Object> getClientToken(Map<String,String> cr) throws Exception {
		return null;
	}

	@Override
	public Map<Object, Object> getClientToken(Map<String,String> cr, boolean needRefresh) throws Exception {
		return null;
	}

	@Override
	public Map<Object, Object> getClientTicket(Map<String,String> cr, Map<Object, Object> tokenMap, boolean needRefresh) throws Exception {
		return null;
	}


//	@Override
//	public String oauth(String code, Map<String,String> cr, String state, String url) throws Exception {
//		return null;
//	}

//	@Override
//	public String parseJson(CustomerUserInfo customerUserInfo) throws Exception {
//		JSONObject jb = null;
//		if (customerUserInfo.getInfoJson() == null || customerUserInfo.getInfoJson().trim().length() == 0) {
//			throw new BussinessException("need infoJson", "need infoJson");
//		} else {
//			try {
//				jb = JSONObject.parseObject(customerUserInfo.getInfoJson());
//			} catch (Exception e) {
//				throw new BussinessException("server exception", "server exception");
//			}
//		}
//		if (jb.get("openid") != null) {
//			customerUserInfo.setAppUniqueId((String) jb.get("openid"));
//		} else {
//			throw new BussinessException("can not find openid", "can not find openid");
//		}
//
//
//		return (String) jb.get("nickname");
//	}
}
