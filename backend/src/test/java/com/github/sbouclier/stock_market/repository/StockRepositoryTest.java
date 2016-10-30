package com.github.sbouclier.stock_market.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

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

	@Test
	public void testStockCount() throws Exception {
		assertThat(stockRepository.count()).isEqualTo(3);
	}

	@Test
	public void testCreateStock() throws Exception {
		stockRepository.save(new Stock("ABC123456789", "STOCK", "New Stock"));

		Stock stock = stockRepository.findByCode("STOCK").get();
		assertThat(stock).isNotNull();
	}

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
}