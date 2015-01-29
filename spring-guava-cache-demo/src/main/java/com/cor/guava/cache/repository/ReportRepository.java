package com.cor.guava.cache.repository;

import com.cor.guava.cache.domain.ReportDTO;

/**
 * Report Repository/DAO interface. Implementations are responsible for
 * generating the Report DTO.
 */
public interface ReportRepository {
	/**
	 * * Generate the Report DTO. * @return Report DTO (processor
	 * intensive/complex to generate)
	 */
	ReportDTO getReport();
}