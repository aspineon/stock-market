package com.github.sbouclier.stock_market.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * StockHistoricalData entity which aims is to historize stocks data
 * 
 * @author Stéphane Bouclier
 *
 */
@Entity
public class StockHistoricalData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@ManyToOne
	private Stock stock;

	@NotNull
	@Temporal(TemporalType.DATE)
	private Date date;

	@NotNull
	private BigDecimal open;

	@NotNull
	private BigDecimal close;

	@NotNull
	private BigDecimal low;

	@NotNull
	private BigDecimal high;

	@NotNull
	private BigDecimal closeAdjusted;

	@NotNull
	private Long volume;

	// ----------------
	// - CONSTRUCTORS -
	// ----------------

	public StockHistoricalData(Date date, BigDecimal open, BigDecimal close, BigDecimal low, BigDecimal high,
			BigDecimal closeAdjusted, Long volume) {
		this.date = date;
		this.open = open;
		this.close = close;
		this.low = low;
		this.high = high;
		this.closeAdjusted = closeAdjusted;
		this.volume = volume;
	}

	public StockHistoricalData(Stock stock, Date date, BigDecimal open, BigDecimal close, BigDecimal low,
			BigDecimal high, BigDecimal closeAdjusted, Long volume) {
		this(date, open, close, low, high, closeAdjusted, volume);
		this.stock = stock;
	}

	// -------------
	// - TO STRING -
	// -------------

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	// -------------------
	// - SETTERS/GETTERS -
	// -------------------

	public Long getId() {
		return id;
	}

	public Stock getStock() {
		return stock;
	}

	public Date getDate() {
		return new Date(date.getTime());
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

	public BigDecimal getCloseAdjusted() {
		return closeAdjusted;
	}

	public Long getVolume() {
		return volume;
	}
}