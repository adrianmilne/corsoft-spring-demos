package com.cor.guava.cache;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cor.guava.cache.CacheConfiguration;
import com.cor.guava.cache.repository.ReportRepository;
import com.cor.guava.cache.repository.ReportRepositoryMockImpl;
import com.google.common.base.Ticker;

/**
 * Overrides the ReportRepository implementation in CacheConfiguration with a
 * specific one for testing.
 *
 */
@Configuration
@EnableCaching
@ComponentScan("com.cor.guava.cache")
public class CacheTestConfiguration extends CacheConfiguration {

	/**
	 * Override the {@link CacheConfiguration} Ticker with a test one.
	 */
	@Override
	@Bean
	public Ticker ticker() {
		return new TestTicker();
	}

	/**
	 * Override the {@link CacheConfiguration} ReportRepository with a test one.
	 */
	@Override
	@Bean
	public ReportRepository reportRepository() {
		return new ReportRepositoryMockImpl();
	}

	/**
	 * Extension to Ticker for testing which will allow us to change the time
	 * manually during testing to check evictions.
	 *
	 */
	class TestTicker extends Ticker {

		private long tickerTime = System.nanoTime();

		@Override
		public long read() {
			return tickerTime;
		}

		public void setTickerTime(long tickerTime) {
			this.tickerTime = tickerTime;
		}
	}

}
