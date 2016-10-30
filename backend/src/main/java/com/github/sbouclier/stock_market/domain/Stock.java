package com.github.sbouclier.stock_market.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Stock entity
 * 
 * @author Stéphane Bouclier
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "uk_stock_isin", columnNames = "isin"),
		@UniqueConstraint(name = "uk_stock_code", columnNames = "code") })
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotNull
	@Length(min = 12, max = 12)
	private String isin;

	@NotNull
	private String code;

	@NotNull
	private String name;

	@NotNull
	@JsonIgnore
	private Date createdDate;

	// ----------------
	// - CONSTRUCTORS -
	// ----------------

	protected Stock() {
	}

	public Stock(String isin, String code, String name) {
		this.isin = isin;
		this.code = code;
		this.name = name;
		this.createdDate = new Date();
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

	public String getIsin() {
		return isin;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Date getCreatedDate() {
		return new Date(createdDate.getTime());
	}
}