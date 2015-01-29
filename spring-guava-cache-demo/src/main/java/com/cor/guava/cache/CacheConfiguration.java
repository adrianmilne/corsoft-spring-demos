package com.cor.guava.cache;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cor.guava.cache.repository.ReportRepository;
import com.cor.guava.cache.repository.ReportRepositoryImpl;
import com.cor.guava.cache.service.ReportService;
import com.cor.guava.cache.service.ReportServiceCachingImpl;
import com.cor.guava.cache.util.LogHelper;
import com.google.common.base.Ticker;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * Spring Annotation Configuration file.
 * 
 * Defines the concrete implementation of interfaces to be injected at runtime.
 *
 */
@Configuration
@EnableCaching
@ComponentScan("com.cor.guava.cache")
public class CacheConfiguration {

	private Log log = LogFactory.getLog(CacheConfiguration.class);

	private static final int CACHE_MAX_SIZE = 1000;
	private static final int CACHE_INTERNAL_EXPIRE_MINUTES = 5;
	private static final int CACHE_PUBLIC_EXPIRE_MINUTES = 60;

	/**
	 * Production Ticker used in the Guava Cache (allows us to override it for
	 * testing).
	 */
	@Bean
	public Ticker ticker() {
		return new Ticker() {
			@Override
			public long read() {
				return System.nanoTime();
			}
		};
	}

	/**
	 * You can use a GuavaCacheManager (see commented code later). However, if
	 * you want to define multiple Guava caches with different properties, you
	 * can use Springs SimpleCacheManager to wrap multiple Guava Caches.
	 * 
	 * @return CacheManager implementation
	 */
	@Bean
	public CacheManager cacheManager() {
		SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
		GuavaCache cache1 = new GuavaCache("public", CacheBuilder.newBuilder()
				.expireAfterWrite(CACHE_PUBLIC_EXPIRE_MINUTES, TimeUnit.MINUTES).maximumSize(CACHE_MAX_SIZE)
				.ticker(ticker()).removalListener(new MyRemovalListener()).build());
		GuavaCache cache2 = new GuavaCache("internal", CacheBuilder.newBuilder()
				.expireAfterWrite(CACHE_INTERNAL_EXPIRE_MINUTES, TimeUnit.MINUTES).maximumSize(CACHE_MAX_SIZE)
				.ticker(ticker()).removalListener(new MyRemovalListener()).build());
		simpleCacheManager.setCaches(Arrays.asList(cache1, cache2));
		return simpleCacheManager;
	}

	/*
	 * Example of using the GuavaCacheManager - simpler if all caches have same
	 * properties. Kept commented here for a quick example - could replace the
	 * cacheManager above with this.
	 */
	// @Bean - NOT USED
	public CacheManager alternativeCacheManager() {
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		cacheManager.setCacheBuilder(CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES)
				.maximumSize(CACHE_MAX_SIZE));
		return cacheManager;
	}

	/**
	 * Report Repository implementation.
	 */
	@Bean
	public ReportRepository reportRepository() {
		return new ReportRepositoryImpl();
	}

	/**
	 * Report Service implementation.
	 */
	@Bean
	public ReportService reportService() {
		return new ReportServiceCachingImpl();
	}

	/**
	 * Example of an optional cache eviction listener.
	 *
	 */
	class MyRemovalListener implements RemovalListener<Object, Object> {

		public void onRemoval(RemovalNotification<Object, Object> notification) {
			log.info(LogHelper.formatString("CACHE: Eviction of " + notification));
		}
	}
}