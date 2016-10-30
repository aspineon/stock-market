package com.github.sbouclier.stock_market.exception;

/**
 * Stock exception
 * 
 * @author Stéphane Bouclier
 *
 */
public class StockException extends RuntimeException {

	private static final long serialVersionUID = -4976095322611578679L;

	public StockException(String message, Throwable cause) {
		super(message, cause);
	}
}