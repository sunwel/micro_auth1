package com.enlink.authcenter.business.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by someone on 2017/11/8.
 */
@Component
@ConfigurationProperties(prefix = "enlink.authcenter.business")
public class AuthBusinessProp {

    public List<Map<String, String>> getResource() {
        return resource;
    }

    public void setResource(List<Map<String, String>> resource) {
        this.resource = resource;
    }

    public List<Map<String,String>> resource = new ArrayList<>();

}
