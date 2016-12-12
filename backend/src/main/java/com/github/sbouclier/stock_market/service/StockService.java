package com.github.sbouclier.stock_market.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.github.sbouclier.stock_market.domain.Stock;

/**
 * StockService
 * 
 * @author Stéphane Bouclier
 *
 */
public interface StockService {

	/**
	 * Retrieve a {@link Stock} by id
	 * 
	 * @param id
	 * @return stock
	 */
	Stock get(Long id);

	/**
	 * Retrieve a {@link Stock} by ISIN
	 * 
	 * @param isin
	 * @return stock
	 */
	Optional<Stock> getByIsin(String isin);

	/**
	 * Retrieve a {@link Stock} by code
	 * 
	 * @param code
	 * @return stock
	 */
	Optional<Stock> getByCode(String code);

	/**
	 * Retrieve all stocks
	 * 
	 * @return stocks
	 */
	List<Stock> getAll();

	/**
	 * Save stick
	 * 
	 * @param stock
	 * @return saved stock
	 */
	Stock save(Stock stock);

	/**
	 * Count all stocks
	 * 
	 * @return stocks numbers
	 */
	Long countAll();

	/**
	 * Load and save historical data from a stock isin
	 * 
	 * @param isin
	 * @param startDate
	 * @param endDate
	 * 
	 * @return stocks added count
	 */
	int loadHistoricalData(String isin, Date startDate, Date endDate);
}