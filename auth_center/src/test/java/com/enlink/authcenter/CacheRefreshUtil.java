package com.enlink.authcenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enlink.authcenter.auth.service.cache.ClientCache;
import com.enlink.authcenter.auth.service.cache.SecretCache;

/**
 * Created by someone on 2017/11/16.
 */
@Component
public class CacheRefreshUtil {

    @Autowired
    ClientCache clientCache;
    @Autowired
    SecretCache secretCache;

    public void clientCacheRefresh() {
        clientCache.refresh();
        clientCache.checkRefresh();
    }

    public void secretCacheRefresh() {
        secretCache.refresh();
        secretCache.checkRefresh();
    }

}
