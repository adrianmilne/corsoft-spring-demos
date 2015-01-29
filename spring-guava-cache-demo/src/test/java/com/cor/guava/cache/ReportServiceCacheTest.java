package com.cor.guava.cache;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import com.cor.guava.cache.CacheTestConfiguration.TestTicker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.cor.guava.cache.domain.ReportDTO;
import com.cor.guava.cache.service.ReportService;
import com.cor.guava.cache.util.LogHelper;

/**
 * Unit Test for {@link ReportService} .
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CacheTestConfiguration.class, loader = AnnotationConfigContextLoader.class)
public class ReportServiceCacheTest {

	private Log log = LogFactory.getLog(ReportServiceCacheTest.class);
	long startTime;
	/**
	 * Inject the Report Service to the Unit Tests
	 */
	@Autowired
	private ReportService reportService;
	/**
	 * This injects the
	 */
	@Autowired
	private TestTicker ticker;

	@Before
	public void setup() {
		startTime = System.nanoTime();
	}

	/**
	 * Test that reports are cached by the Service layer.
	 */
	@Test
	public void testGetCachedInternalReport() throws Exception {
		log.info("\n\n");
		log.info(LogHelper.formatString("SCENARIO 1: testGetCachedInternalReport"));
		// Get Report first time
		log.info("\n* TEST - Get Report");
		ReportDTO report1 = reportService.getInternalReport();
		// Get Report again - will return cached version
		log.info("\n* TEST - Get Report *CACHED*");
		ReportDTO report2 = reportService.getInternalReport();
		// Get Report again - will return cached version
		log.info("\n* TEST - Get Report *CACHED*");
		ReportDTO report3 = reportService.getInternalReport();
		// Verify that all report
		assertThat(report2, is(equalTo(report1)));
		assertThat(report3, is(equalTo(report1)));
	}

	/**
	 * Test that cached reports are evicted based on the cache configuration.
	 */
	@Test
	public void testGetExpiredInternalReport() throws Exception {
		log.info("\n\n");
		log.info(LogHelper.formatString("SCENARIO 2: testGetExpiredInternalReport"));
		// Get Report first time log.info("\n* TEST - Get Report");
		ReportDTO report1 = reportService.getInternalReport();
		advanceTickerTime(7);
		// Get Report again - should be evicted and generate a new report
		log.info("\n* TEST - Get Report *NOT CACHED*");
		ReportDTO report2 = reportService.getInternalReport();
		// Verify this report is not cached
		assertThat(report2, not(equalTo(report1)));
	}

	@Test
	public void testGetExpiredPublicReport() throws Exception {
		log.info("\n\n");
		log.info(LogHelper.formatString("SCENARIO 3: testGetExpiredPublicReport"));
		// Get Report first time
		log.info("\n* TEST - Get Report");
		ReportDTO report1 = reportService.getPublicReport();
		advanceTickerTime(70);
		// Get Report again - should be evicted and generate a new report
		log.info("\n* TEST - Get Report *NOT CACHED*");
		ReportDTO report2 = reportService.getPublicReport();
		// Verify this report is not cached
		assertThat(report2, not(equalTo(report1)));
	}

	/**
	 * Helper to artificially advance the time - allowing us to force eviction
	 * from the cache.
	 * 
	 * @param minutes
	 *            minutes to advance time by
	 */
	private void advanceTickerTime(int minutes) {
		long before = ticker.read();
		long minutesAsNonoseconds = minutes * 60000000000L;
		ticker.setTickerTime(startTime + minutesAsNonoseconds);
		long after = ticker.read();
		log.info("ADVANCING TIME - BEFORE:" + before + ", AFTER:" + after + ", MINS="
				+ ((after - before) / 60000000000L));
	}

}