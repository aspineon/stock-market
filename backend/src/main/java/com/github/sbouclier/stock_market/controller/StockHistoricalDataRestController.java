package com.github.sbouclier.stock_market.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.sbouclier.stock_market.exception.StockException;
import com.github.sbouclier.stock_market.service.StockService;
import com.github.sbouclier.stock_market.service.YahooFinanceService;

/**
 * StockHistoricalDataRest REST controller
 * 
 * @author Stéphane Bouclier
 *
 */
@RestController
@RequestMapping("api/stocks/historical")
public class StockHistoricalDataRestController {

	private static final Logger log = LoggerFactory.getLogger(StockHistoricalDataRestController.class);

	// TODO remove
	private final YahooFinanceService yahooFinanceService;

	private final StockService stockService;

	// ----------------
	// - CONSTRUCTORS -
	// ----------------

	@Autowired
	public StockHistoricalDataRestController(StockService stockService, YahooFinanceService yahooFinanceService) {
		this.stockService = stockService;
		this.yahooFinanceService = yahooFinanceService;
	}

	// ------------
	// - REQUESTS -
	// ------------·

	@RequestMapping(value = "/load", method = RequestMethod.GET)
	public int load(@RequestParam String isin, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		log.debug("load(isin={}, startDate={}, endDate={})", isin, startDate, endDate);

		int count = 0;
		try {
			count = stockService.loadHistoricalData(isin, startDate, endDate);
		} catch (Exception ex) {
			throw new StockException("Unable to load historical data", ex);
		}

		return count;
	}
}