package com.cor.guava.cache.domain;

import java.util.Date;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * This is a simple example Data Transfer Object - something that would be used
 * for reporting purposes, and is quite processor intensive/complex to build up,
 * and is not too time-sensitive - so can be cached
 *
 */
public class ReportDTO {
	/*
	 * Date report was generated
	 */
	private Date dateGenerated;
	/*
	 * Some count that is complex/intensive to calculate - making this report an
	 * ideal candidate for caching
	 */
	private long count;

	public Date getDateGenerated() {
		return dateGenerated;
	}

	public void setDateGenerated(Date dateGenerated) {
		this.dateGenerated = dateGenerated;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "ReportDTO [dateGenerated=" + dateGenerated + ", count=" + count + "]";
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}