package com.github.sbouclier.stock_market.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.github.sbouclier.stock_market.domain.Stock;
import com.github.sbouclier.stock_market.exception.StockException;
import com.github.sbouclier.stock_market.exception.StockNotFoundException;
import com.github.sbouclier.stock_market.service.StockService;

/**
 * Stock REST controller
 * 
 * @author Stéphane Bouclier
 *
 */
@RestController
@RequestMapping("api/stocks")
public class StockRestController {

	private static final Logger log = LoggerFactory.getLogger(StockRestController.class);

	private final StockService stockService;

	// ----------------
	// - CONSTRUCTORS -
	// ----------------

	@Autowired
	public StockRestController(StockService stockService) {
		this.stockService = stockService;
	}

	// ------------
	// - REQUESTS -
	// ------------

	@RequestMapping(method = RequestMethod.POST)
	ResponseEntity<?> createStock(@RequestBody Stock stock) {
		log.debug("createStock(stock={})", stock);

		final Stock newStock = stockService.save(new Stock(stock.getIsin(), stock.getCode(), stock.getName()));

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(newStock.getId()).toUri());

		return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{isin}", method = RequestMethod.GET)
	public Stock readStock(@PathVariable String isin) {
		log.debug("readStock(isin={})", isin);
		return stockService.getByIsin(isin).orElseThrow(() -> new StockNotFoundException(isin));
	}

	@RequestMapping(method = RequestMethod.GET)
	public Collection<Stock> readStocks() {
		log.debug("readStocks()");
		return stockService.getAll();
	}

	@ControllerAdvice
	class StockControllerAdvice {

		@ResponseBody
		@ExceptionHandler(StockNotFoundException.class)
		@ResponseStatus(HttpStatus.NOT_FOUND)
		VndErrors stockNotFoundExceptionHandler(StockNotFoundException ex) {
			return new VndErrors("error", ex.getMessage());
		}

		@ResponseBody
		@ExceptionHandler(StockException.class)
		@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
		VndErrors stockExceptionHandler(StockException ex) {
			return new VndErrors("error", ex.getMessage());
		}
	}
}