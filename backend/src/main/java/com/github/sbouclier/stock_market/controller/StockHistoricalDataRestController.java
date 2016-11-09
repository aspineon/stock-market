package com.github.sbouclier.stock_market.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.sbouclier.stock_market.domain.StockHistoricalData;
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

	private final YahooFinanceService yahooFinanceService;

	// ----------------
	// - CONSTRUCTORS -
	// ----------------

	@Autowired
	public StockHistoricalDataRestController(YahooFinanceService yahooFinanceService) {
		this.yahooFinanceService = yahooFinanceService;
	}

	// ------------
	// - REQUESTS -
	// ------------

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public List<StockHistoricalData> getHistoricalData(@PathVariable String code,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
			@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
		log.debug("getHistoricalData(code={}, startDate={}, endDate={})");

		List<StockHistoricalData> stockHistoricalData = new ArrayList<>();

		final List<YahooFinanceService.CSVData> data = yahooFinanceService.getStockData(code, startDate, endDate);

		StockHistoricalData shd = null;
		for (YahooFinanceService.CSVData csvData : data) {
			shd = new StockHistoricalData(csvData.getDate(), csvData.getOpen(), csvData.getClose(), csvData.getLow(),
					csvData.getHigh(), csvData.getAdjClose(), csvData.getVolume());
			stockHistoricalData.add(shd);
		}

		return stockHistoricalData;
	}
}