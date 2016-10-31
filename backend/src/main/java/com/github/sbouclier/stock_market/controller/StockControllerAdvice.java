package com.github.sbouclier.stock_market.controller;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.github.sbouclier.stock_market.exception.StockException;
import com.github.sbouclier.stock_market.exception.StockNotFoundException;

/**
 * Stock controller advice
 * 
 * @author Stéphane Bouclier
 *
 */
@ControllerAdvice
public class StockControllerAdvice {

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