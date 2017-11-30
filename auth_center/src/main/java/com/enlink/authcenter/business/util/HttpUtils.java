package com.enlink.authcenter.business.util;

import com.enlink.authcenter.business.exception.BussinessException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;

/**
 * Created by Zhaowy on 2014/9/3.
 */
@Component
public class HttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	@Autowired
	private PoolingHttpClientConnectionManager httpPoolManager;

	private CloseableHttpClient client;

	{
//		HttpParams params = loadHttpConfFromFile();

//		SchemeRegistry registry = new SchemeRegistry();
//		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//		registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));

//		httpPoolManager = new PoolingHttpClientConnectionManager();
//		// 将最大连接数增加到200
//		httpPoolManager.setMaxTotal(150);
//		// 将每个路由基础的连接增加到20
//		httpPoolManager.setDefaultMaxPerRoute(50);
		String encoding = "UTF-8";

		RequestConfig reqconfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(15000).setStaleConnectionCheckEnabled(true).build();
		client = HttpClients.custom()
				.setConnectionManager(httpPoolManager).setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).setDefaultRequestConfig(reqconfig)
				.build();
		logger.info("httputils 初始化");
	}

	public String post(String url, String encoding, String content) throws UnsupportedEncodingException {
		byte[] resp = post(url, content.getBytes(encoding));
		if (null == resp) {
			return null;
		}

		return new String(resp, encoding);
	}


	public String post(String url, String content) throws UnsupportedEncodingException {
		return post(url, "UTF-8", content);
	}


	public byte[] post(String url, byte[] content) {
		try {
			byte[] ret = post(url, new ByteArrayEntity(content));
			return ret;
		} catch (Exception e) {
			return null;
		}
	}

	public byte[] post(String url, HttpEntity requestEntity) throws Exception {


		HttpPost method = new HttpPost(url);
		method.addHeader("Connection", "Keep-Alive");
//		method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
//        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
//        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		method.setEntity(requestEntity);


		try {
			CloseableHttpResponse response = client.execute(method);

			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new BussinessException(statusCode+"","httpstatus error");
			}
			return EntityUtils.toByteArray(response.getEntity());


		} catch (SocketTimeoutException e) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			method.releaseConnection();
		}
	}

	public byte[] get(String url) throws Exception {

		HttpGet getMethod = new HttpGet(url);
		getMethod.addHeader("Connection", "Keep-Alive");
//        method.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
//        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(0, false));
		getMethod.addHeader("Content-Type", "application/x-www-form-urlencoded");

		try {
			CloseableHttpResponse response = client.execute(getMethod);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				throw new BussinessException(statusCode+"","httpstatus error");
			}
			return EntityUtils.toByteArray(response.getEntity());


		} catch (SocketTimeoutException e) {
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			getMethod.releaseConnection();
		}
	}


}