package com.cor.guava.cache.service;

import com.cor.guava.cache.domain.ReportDTO;

/**
 * Reporting Service Interface.
 */
public interface ReportService {
	/**
	 * * Get Report for public consumption. This is not very time sensitive - *
	 * needs only be updated on a daily basis. * * @return Report DTO (quite
	 * intensive to produce)
	 */
	ReportDTO getPublicReport();

	/**
	 * * Get Report for internal company consumption. This needs to be updated *
	 * more frequently. * * @return Report DTO (quite intensive to produce)
	 */
	ReportDTO getInternalReport();
}
