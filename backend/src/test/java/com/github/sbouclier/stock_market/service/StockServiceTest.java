package com.github.sbouclier.stock_market.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.sbouclier.stock_market.domain.Stock;
import com.github.sbouclier.stock_market.exception.StockException;

/**
 * Stock service tests
 * 
 * @author Stéphane Bouclier
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class StockServiceTest {

	@Autowired
	private StockService stockService;

	// ---------- create stock ----------

	@Test
	public void createValidStock() {
		final Stock stock = stockService.save(new Stock("ABC123456789", "NEW", "New stock"));
		assertNotNull(stock);
		assertEquals(4L, stock.getId().longValue());
		assertEquals("ABC123456789", stock.getIsin());
	}

	@Test(expected = StockException.class)
	public void createStockWithInvalidIsin() {
		stockService.save(new Stock("ABC", "NEW", "New stock"));
	}

	@Test(expected = StockException.class)
	public void createStockWithNotCode() {
		stockService.save(new Stock("ABC123456789", null, "New stock"));
	}

	@Test(expected = StockException.class)
	public void createStockWithBlankCode() {
		stockService.save(new Stock("ABC123456789", null, "New stock"));
	}

	@Test(expected = StockException.class)
	public void createStockWithNotName() {
		stockService.save(new Stock("ABC123456789", "NEW", null));
	}

	@Test(expected = StockException.class)
	public void createStockWithBlankName() {
		stockService.save(new Stock("ABC123456789", "NEW", ""));
	}

	// ---------- get stock ----------

	@Test
	public void getStockWithValidId() {
		final Stock stock = stockService.get(2L);
		assertNotNull(stock);
		assertEquals("US02079K3059", stock.getIsin());
	}

	@Test
	public void getStockWithInvalidId() {
		final Stock stock = stockService.get(999L);
		assertNull(stock);
	}
	
	@Test
	public void getStockWithValidIsin() {
		final Optional<Stock> stock = stockService.getByIsin("US02079K3059");
		assertTrue(stock.isPresent());
		assertEquals("GOOGL", stock.get().getCode());
	}

	@Test
	public void getStockWithInvalidIsin() {
		final Optional<Stock> stock = stockService.getByIsin("UNKNOWN");
		assertFalse(stock.isPresent());
	}

	@Test
	public void getStockWithValidCode() {
		final Optional<Stock> stock = stockService.getByCode("MSFT");
		assertTrue(stock.isPresent());
		assertEquals("US5949181045", stock.get().getIsin());
	}

	@Test
	public void getStockWithInvalidCode() {
		final Optional<Stock> stock = stockService.getByCode("UNKNOWN");
		assertFalse(stock.isPresent());
	}
	
	// ---------- get all stocks ----------
	
	@Test
	public void getAllStocks() {
		final List<Stock> stocks = stockService.getAll();
		assertEquals(3, stocks.size());
		assertThat(stocks, hasItem(Matchers.<Stock>hasProperty("isin", equalTo("US0378331005"))));
		assertThat(stocks, hasItem(Matchers.<Stock>hasProperty("isin", equalTo("US02079K3059"))));
		assertThat(stocks, hasItem(Matchers.<Stock>hasProperty("isin", equalTo("US5949181045"))));
	}
	
	// ---------- count stocks ----------

	@Test
	public void countStocks() {
		assertEquals(3L, stockService.countAll().longValue());
	}
}