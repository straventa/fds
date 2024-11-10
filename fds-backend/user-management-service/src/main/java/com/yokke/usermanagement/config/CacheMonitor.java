package com.yokke.usermanagement.config;

import io.lettuce.core.support.caching.RedisCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CacheMonitor {

    private final CacheManager cacheManager;


    @Scheduled(fixedRate = 10000) // Run every 5 minutes
    public void logCacheStatistics() {
        cacheManager.getCacheNames().forEach(cacheName -> {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache instanceof RedisCache redisCache) {
                log.info("Cache '{}' statistics:", cacheName);
                // Add your cache statistics logging here
            }
        });
    }
}