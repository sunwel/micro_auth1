package com.enlink.authcenter.business.dao;

import com.enlink.authcenter.business.config.AuthBusinessProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by someone on 2017/11/8.
 */
@Component
public class CustomerResourceCache {

    private List<Map<String, String>> customerResourceList;

    @Autowired
    AuthBusinessProp authBusinessProp;

    @PostConstruct
    public void init() {
        this.customerResourceList = authBusinessProp.getResource();
    }

    /**
     * 通过resourceId获取
     */
    public Map<String, String> getCustResByResId(String resourceId) {
        for (Map<String, String> map : this.customerResourceList) {
            if (map.get("resourceId").equals(resourceId)) {
                return map;
            }
        }
        return null;
    }

}
