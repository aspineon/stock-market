package com.github.sbouclier.stock_market.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void test() {
		log.debug("test()");
		try {
			yahooFinanceService.getStockData("", null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}