package com.cor.guava.cache.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cor.guava.cache.domain.ReportDTO;
import com.cor.guava.cache.repository.ReportRepository;
import com.cor.guava.cache.util.LogHelper;

/**
 * Implementation of {@link ReportService}.
 */
@Service
public class ReportServiceCachingImpl implements ReportService {

	private Log log = LogFactory.getLog(ReportServiceCachingImpl.class);

	@Autowired
	private ReportRepository reportRepository;

	/**
	 * {@inheritDoc}
	 */
	@Cacheable("public")
	public ReportDTO getPublicReport() {
		log.info(LogHelper.formatString("SERVICE: getPublicReport"));
		return reportRepository.getReport();
	}

	/**
	 * {@inheritDoc}
	 */
	@Cacheable("internal")
	public ReportDTO getInternalReport() {
		log.info(LogHelper.formatString("SERVICE: getInternalReport"));
		return reportRepository.getReport();
	}

}