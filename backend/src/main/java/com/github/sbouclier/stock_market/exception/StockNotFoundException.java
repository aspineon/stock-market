package com.github.sbouclier.stock_market.exception;

/**
 * Stock not found exception
 * 
 * @author Stéphane Bouclier
 *
 */
public class StockNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7680605476914748500L;

	public StockNotFoundException(String isin) {
		super("could not find stock '" + isin + "'.");
	}
}