package com.enlink.authcenter.business.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.enlink.authcenter.business.dao.CustomerResourceCache;
import com.enlink.authcenter.business.exception.BussinessException;
import com.enlink.authcenter.business.oauth.AbstractCustomerOauth;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

@Service("customerOauthService")
public class CustomerOauthServiceImpl implements CustomerOauthService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerOauthServiceImpl.class);
	@Autowired
	CustomerResourceCache customerResourceCache;
	@Autowired
	private Map<String, AbstractCustomerOauth> customerOauthTypes;

	@Override
	public String Oauth(String resourceId, String code, String state, String url, int subscribe) throws Exception {
		Map<String, String> cr = customerResourceCache.getCustResByResId(resourceId);
		if (cr == null) {
			throw new BussinessException("找不到resourceId");
		}
		AbstractCustomerOauth oauth = this.customerOauthTypes.get(cr.get("source"));

		String user_token_string = oauth.getAccessToken(code, cr);
		String usertoken = null;
		String openId = null;
		String scope = null;
		if (!StringUtils.isEmpty(user_token_string)) {
			JSONObject jb = JSON.parseObject(user_token_string);
			usertoken = (String) jb.get("access_token");
			openId = (String) jb.get("openid");
			scope = (String) jb.get("scope");
		}
		if (subscribe == 1) {
			// 需要关注
			Map<Object, Object> tokenmap = oauth.getClientToken(cr);
			String userinfo = oauth.getInfoByClient(String.valueOf(tokenmap.get("token")), openId);
			if (!userinfo.contains("errcode") && !StringUtils.isEmpty(userinfo)) {
				JSONObject infojb = JSON.parseObject(userinfo);
				int user_subscribe = infojb.getInteger("subscribe");
				Map<String, String> params = new HashMap<>();
				if (user_subscribe == 1) {
					String infokey = "AUTH:OAUTH:USERINFO:" + DigestUtils.md5Hex(userinfo);
					// 验证通过，返回资料
					Map<String, String> smartinfomap = new HashMap<>();
					smartinfomap.put("openid", (String) infojb.get("openid"));
					smartinfomap.put("nickname", (String) infojb.get("nickname"));
					smartinfomap.put("unionid", (String) infojb.get("unionid"));
					smartinfomap.put("infokey", infokey);
					params.put("info", JSON.toJSONString(smartinfomap));
					params.put("state", state);
					return "redirect:" + oauth.urlParam(url, params, cr.get("resourceId"));
				} else {
					// 验证失败,返回错误
					if (url.contains("?")) {
						url = url + "&";
					} else {
						url = url + "?";
					}
					// 304 用户未关注
					return "redirect:" + url + "errorcode=304";
				}

			} else {
				if (url.contains("?")) {
					url = url + "&";
				} else {
					url = url + "?";
				}
				// 303 获取用户资料失败
				return "redirect:" + url + "errorcode=303";
			}
		}
		if (!StringUtils.isEmpty(scope) && scope.equals("snsapi_userinfo")) {
			// 获取资料
			String info = oauth.getInfoByUserToken(usertoken, cr, openId);
			if (url.contains("?")) {
				url = url + "&";
			} else {
				url = url + "?";
			}
			if (StringUtils.isEmpty(info)) {
				// 无返回值,跳转错误页面
				return "redirect:" + url + "errorcode=2";
			} else {
				// 验证通过，返回资料
				Map<String, String> params = new HashMap<>();
				String infokey = "AUTH:OAUTH:USERINFO:" + DigestUtils.md5Hex(info);
				JSONObject infojb = JSON.parseObject(info);
				Map<String, String> smartinfomap = new HashMap<>();
				smartinfomap.put("openid", (String) infojb.get("openid"));
				smartinfomap.put("nickname", (String) infojb.get("nickname"));
				smartinfomap.put("unionid", (String) infojb.get("unionid"));
				smartinfomap.put("infokey", infokey);
				params.put("info", JSON.toJSONString(smartinfomap));
				params.put("state", state);
				return "redirect:" + oauth.urlParam(url, params, cr.get("resourceId"));
			}
		}
		// 验证通过，返回资料
		Map<String, String> params = new HashMap<>();
		params.put("info", user_token_string);
		params.put("state", state);
		return "redirect:" + oauth.urlParam(url, params, cr.get("resourceId"));
	}

	@Override
	public Map<Object, Object> getCliToken(String resourceId, boolean needRefresh) throws Exception {
		Map<String, String> cr = customerResourceCache.getCustResByResId(resourceId);
		if (cr == null) {
			throw new BussinessException("找不到resourceId");
		}
		return customerOauthTypes.get(cr.get("source")).getClientToken(cr, needRefresh);
	}

	@Override
	public Map<Object, Object> getCliTicket(String resourceId, boolean needRefresh) throws Exception {
		Map<String, String> cr = customerResourceCache.getCustResByResId(resourceId);
		if (cr == null) {
			throw new BussinessException("找不到resourceId");
		}
		Map<Object, Object> tokenMap = customerOauthTypes.get(cr.get("source")).getClientToken(cr, needRefresh);
		return customerOauthTypes.get(cr.get("source")).getClientTicket(cr, tokenMap, needRefresh);
	}

	@Override
	public Map<Object, Object> getCliJSTicket(String resourceId, String url) throws Exception {
		Map<String, String> cr = customerResourceCache.getCustResByResId(resourceId);
		if (cr == null) {
			throw new BussinessException("找不到resourceId");
		}
		if (StringUtils.isEmpty(url)) {
			throw new BussinessException("url参数为空");
		}
		Map<Object, Object> ticketMap = this.getCliTicket(resourceId, false);
		String nonceStr = getNonceStr();
		String timestamp = (System.currentTimeMillis() / 1000) + "";
		Map<Object, Object> parammap = new TreeMap<>();
		parammap.put("noncestr", nonceStr);
		parammap.put("timestamp", timestamp);
		parammap.put("url", url);
		parammap.put("jsapi_ticket", ticketMap.get("jsticket"));
		String paramvalue = "";
		for (Map.Entry<Object, Object> paramEntry : parammap.entrySet()) {
			paramvalue += paramEntry.getKey() + "=" + paramEntry.getValue() + "&";
		}
		paramvalue = paramvalue.substring(0, paramvalue.length() - 1);
		String signature = DigestUtils.sha1Hex(paramvalue);
		parammap.put("signature", signature);
		parammap.put("appid", cr.get("appId"));
		logger.info("getClientTicket请求返回：map={}", parammap);
		return parammap;
	}

	private String getNonceStr() {
		Random random = new Random();
		return DigestUtils.md5Hex(String.valueOf(random.nextInt(10000)));
	}
}
