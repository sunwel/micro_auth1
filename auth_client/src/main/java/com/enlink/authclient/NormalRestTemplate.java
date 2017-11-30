package com.enlink.authclient;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 普通请求的RestTemplate<br/>
 * 区别于MicroTokenRestTemplate
 * @author Timothy
 */
@Component
public class NormalRestTemplate extends RestTemplate {

}
