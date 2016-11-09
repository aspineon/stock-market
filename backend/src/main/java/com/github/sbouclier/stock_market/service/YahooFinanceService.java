package com.github.sbouclier.stock_market.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.github.sbouclier.stock_market.exception.StockException;

/**
 * Service for Yahoo financial API
 * 
 * @author Stéphane Bouclier
 *
 */
public interface YahooFinanceService {

	/**
	 * Retrieve historical stock data between dates
	 * 
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws ParseException
	 */
	List<CSVData> getStockData(String code, Date startDate, Date endDate)
			throws StockException;

	/**
	 * 
	 * Data result from getStockData call
	 *
	 */
	static class CSVData {
		private final Date date;
		private final BigDecimal open;
		private final BigDecimal close;
		private final BigDecimal low;
		private final BigDecimal high;
		private final BigDecimal adjClose;
		private final Long volume;

		public CSVData(Date date, BigDecimal open, BigDecimal close, BigDecimal low, BigDecimal high,
				BigDecimal adjClose, Long volume) {
			this.date = date;
			this.open = open;
			this.close = close;
			this.low = low;
			this.high = high;
			this.adjClose = adjClose;
			this.volume = volume;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}

		public Date getDate() {
			return date;
		}

		public BigDecimal getOpen() {
			return open;
		}

		public BigDecimal getClose() {
			return close;
		}

		public BigDecimal getLow() {
			return low;
		}

		public BigDecimal getHigh() {
			return high;
		}

		public BigDecimal getAdjClose() {
			return adjClose;
		}

		public Long getVolume() {
			return volume;
		}
	}
}