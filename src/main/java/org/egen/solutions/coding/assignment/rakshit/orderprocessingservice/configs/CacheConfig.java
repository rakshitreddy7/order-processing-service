package org.egen.solutions.coding.assignment.rakshit.orderprocessingservice.configs;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import net.sf.ehcache.config.CacheConfiguration;

import static net.sf.ehcache.CacheManager.newInstance;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

    @Bean
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration orderByOrderIdCache = new CacheConfiguration();
        orderByOrderIdCache.setName("orderByOrderIdCache");
        orderByOrderIdCache.setMemoryStoreEvictionPolicy("LRU");
        orderByOrderIdCache.setMaxEntriesLocalHeap(100);
        orderByOrderIdCache.setTimeToLiveSeconds(10);

        CacheConfiguration ordersByCustomerIdCache = new CacheConfiguration();
        ordersByCustomerIdCache.setName("ordersByCustomerIdCache");
        ordersByCustomerIdCache.setMemoryStoreEvictionPolicy("LRU");
        ordersByCustomerIdCache.setMaxEntriesLocalHeap(100);
        ordersByCustomerIdCache.setTimeToLiveSeconds(10);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(orderByOrderIdCache);
        config.addCache(ordersByCustomerIdCache);
        return newInstance(config);
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }
}
