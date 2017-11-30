package com.enlink.authclient;

import org.springframework.web.client.RestTemplate;

/**
 * 包含微服务Token的专用RestTemplate<br/>
 * 使用此RestTemplate需要：<br/>
 * 提供ClientHttpRequestFactory类型的Bean，建议使用HttpCommons提供的基于连接池的实例
 * @author Timothy
 */
public class MicroTokenRestTemplate extends RestTemplate {

}
