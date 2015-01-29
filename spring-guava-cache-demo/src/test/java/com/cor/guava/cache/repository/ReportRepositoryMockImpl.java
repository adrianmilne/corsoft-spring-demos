package com.cor.guava.cache.repository;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

import com.cor.guava.cache.domain.ReportDTO;
import com.cor.guava.cache.repository.ReportRepository;
import com.cor.guava.cache.util.LogHelper;

/**
 * Test implementation of {@link ReportRepositoy}.
 *
 */
@Repository
public class ReportRepositoryMockImpl implements ReportRepository {

	private Log log = LogFactory.getLog(ReportRepositoryMockImpl.class);

	/**
	 * {@inheritDoc}
	 */
	public ReportDTO getReport() {
		ReportDTO report = new ReportDTO();
		report.setCount(System.currentTimeMillis());
		report.setDateGenerated(new Date());
		log.info(LogHelper.formatString("REPOSITORY: Generating Report : " + report));
		return report;
	}
}