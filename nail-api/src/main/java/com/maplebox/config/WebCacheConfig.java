package com.maplebox.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@ComponentScan(basePackages = "com.maplebox.cache")
@EnableCaching(mode = AdviceMode.PROXY, proxyTargetClass = true)
public class WebCacheConfig {
	static Logger log = LoggerFactory.getLogger(WebCacheConfig.class);
	
	@Bean
	public EhCacheManagerFactoryBean cacheManagerFactory() {
		EhCacheManagerFactoryBean cacheManagerFactory = new EhCacheManagerFactoryBean();
		cacheManagerFactory.setConfigLocation(new ClassPathResource("ehcache.xml"));
		cacheManagerFactory.setShared(true);
		return cacheManagerFactory;
	}
	
	@Bean
	public CacheManager cacheManager(EhCacheManagerFactoryBean cacheManagerFactory) {
		EhCacheCacheManager cacheManager = new EhCacheCacheManager();
		cacheManager.setCacheManager(cacheManagerFactory.getObject());
		return cacheManager;
	}
}
