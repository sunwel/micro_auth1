package com.enlink.authcenter.business.oauth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.enlink.authcenter.business.util.HttpUtils;
import com.enlink.authcenter.business.exception.BussinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("weichat")
public class WeichatOauth extends AbstractCustomerOauth {

	private static final Logger logger = LoggerFactory.getLogger(WeichatOauth.class);
	@Autowired
	private HttpUtils httpUtils;

	@Override
	public String getAccessToken(String code, Map<String, String> cr) throws Exception {
		String appid = cr.get("appId");
		String appkey = cr.get("appKey");
		logger.info("(getAccessToken)向腾讯微信发送请求......");
		String tokenurl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret=" + appkey
				+ "&grant_type=authorization_code&code=" + code;
		byte[] tokenbyte = httpUtils.get(tokenurl);
		if (tokenbyte == null) {
			// 无返回值,跳转错误页面
			throw new BussinessException("can't find token", "无法获取到token");
		}
		logger.info("(getAccessToken)腾讯微信返回成功......");
		String resp = new String(tokenbyte, "UTF-8");
		return resp;
	}

	@Override
	public String getInfoByClient(String clientToken, String openid) throws Exception {
		String infoUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + clientToken + "&openid=" + openid
				+ "&lang=zh_CN";
		logger.info("(getInfoByClient)向腾讯微信发送请求......");
		byte[] infobyte = httpUtils.get(infoUrl);
		if (infobyte == null) {
			// 无返回值,跳转错误页面
			throw new BussinessException("can't find info", "无法获取到info");
		}
		logger.info("(getInfoByClient)腾讯微信返回成功......");
		String info = new String(infobyte, "UTF-8");
		return info;
	}

	@Override
	public String getInfoByUserToken(String user_access_token, Map<String, String> cr, String openid) throws Exception {
		String infourl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + user_access_token + "&openid="
				+ openid + "&lang=zh_CN";
		logger.info("(getInfoByUserToken)向腾讯微信发送请求......");
		byte[] infobyte = httpUtils.get(infourl);
		if (infobyte == null) {
			throw new BussinessException("can't find info", "无法获取到info");
		}
		logger.info("(getInfoByUserToken)腾讯微信返回成功......");
		String infoString = new String(infobyte, "UTF-8");
		return infoString;
	}

	@Override
	public Map<Object, Object> getClientToken(Map<String, String> cr) throws Exception {
		return this.getClientToken(cr, false);
	}

	@Override
	public Map<Object, Object> getClientToken(Map<String, String> cr, boolean needRefresh) throws Exception {
		String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ cr.get("appId") + "&secret=" + cr.get("appKey");
		logger.info("(getClientToken)向腾讯微信发送请求......");
		byte[] infobyte = httpUtils.get(token_url);
		if (infobyte == null || infobyte.length == 0) {
			throw new BussinessException("can't get token", "无法获取到Token");
		}
		logger.info("(getClientToken)腾讯微信返回成功......");
		String tokenStr = new String(infobyte, "UTF-8");
		if (tokenStr.contains("errcode")) {
			throw new BussinessException(tokenStr, tokenStr);
		}
		JSONObject jb = JSON.parseObject(tokenStr);
		String token = jb.getString("access_token");
		// int expire_in = (int) jb.getInteger("expires_in");

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		Date expireDate = calendar.getTime();
		String expire_timestamp = expireDate.getTime() + "";
		String expire_timestamp_short = expire_timestamp.substring(0, 10);
		Map<Object, Object> redisMap = new HashMap<>();
		redisMap.put("token", token);
		redisMap.put("expire_timestamp", expire_timestamp);
		redisMap.put("expire_timestamp_short", expire_timestamp_short);
		logger.info("getClientToken请求返回：map={}", redisMap);
		return redisMap;
	}

	@Override
	public Map<Object, Object> getClientTicket(Map<String, String> cr, Map<Object, Object> tokenMap,
			boolean needRefresh) throws Exception {
		String token_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + tokenMap.get("token")
				+ "&type=jsapi";
		logger.info("(getClientTicket)向腾讯微信发送请求......");
		byte[] infobyte = httpUtils.get(token_url);
		if (infobyte == null || infobyte.length == 0) {
			throw new BussinessException("can't get ticket", "无法获取到Ticket");
		}
		logger.info("(getClientTicket)腾讯微信返回成功......");
		String ticketStr = new String(infobyte, "UTF-8");
		JSONObject ticketjb = JSON.parseObject(ticketStr);
		int errorcode = ticketjb.getInteger("errcode");
		String ticket = ticketjb.getString("ticket");
		if (ticketStr.contains("errcode") && errorcode != 0) {
			throw new BussinessException(ticketStr, ticketStr);
		}

		Map<Object, Object> redisMap = new HashMap<>();
		redisMap.put("jsticket", ticket);
		redisMap.put("expire_timestamp", tokenMap.get("expire_timestamp"));
		redisMap.put("expire_timestamp_short", tokenMap.get("expire_timestamp_short"));
		logger.info("getClientTicket请求返回：map={}", redisMap);
		return redisMap;
	}
}
