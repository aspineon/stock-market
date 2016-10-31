package com.github.sbouclier.stock_market.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.sbouclier.stock_market.domain.Stock;

/**
 * Stock repository tests
 * 
 * @author Stéphane Bouclier
 *
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class StockRepositoryTest {

	@Autowired
	private StockRepository stockRepository;

	// ---------- count stocks ----------
	
	@Test
	public void testStockCount() throws Exception {
		assertThat(stockRepository.count()).isEqualTo(3);
	}
	
	// ---------- create stock ----------

	@Test
	public void testCreateStock() throws Exception {
		stockRepository.save(new Stock("ABC123456789", "STOCK", "New Stock"));

		Stock stock = stockRepository.findByCode("STOCK").get();
		assertThat(stock).isNotNull();
	}
	
	// ---------- find stock by code ----------

	@Test
	public void testGetStockByValidCode() {
		Optional<Stock> stock = stockRepository.findByCode("AAPL");

		assertTrue(stock.isPresent());
		assertThat(stock.get().getIsin()).isEqualTo("US0378331005");
	}

	@Test
	public void testGetStockByInvalidCode() {
		assertFalse(stockRepository.findByCode("UNKNOWN").isPresent());
	}
	
	// ---------- find by isin ----------

	@Test
	public void testGetStockByValidIsin() {
		Optional<Stock> stock = stockRepository.findByIsin("US02079K3059");

		assertTrue(stock.isPresent());
		assertThat(stock.get().getName()).isEqualTo("Google");
	}

	@Test
	public void testGetStockByInvalidIsin() {
		assertFalse(stockRepository.findByIsin("UNKNOWN").isPresent());
	}
	
	// ---------- find all ----------
	
	@Test
	public void testGetAllStock() {
		final List<Stock> stocks = stockRepository.findAll();
		assertEquals(3, stocks.size());
		assertThat(stocks, hasItem(Matchers.<Stock>hasProperty("isin", equalTo("US0378331005"))));
		assertThat(stocks, hasItem(Matchers.<Stock>hasProperty("isin", equalTo("US02079K3059"))));
		assertThat(stocks, hasItem(Matchers.<Stock>hasProperty("isin", equalTo("US5949181045"))));
	}
}