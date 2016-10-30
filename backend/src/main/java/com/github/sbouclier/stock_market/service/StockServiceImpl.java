package com.github.sbouclier.stock_market.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sbouclier.stock_market.domain.Stock;
import com.github.sbouclier.stock_market.exception.StockException;
import com.github.sbouclier.stock_market.repository.StockRepository;

/**
 * Default {@link StockService} implementation
 * 
 * @author Stéphane Bouclier
 *
 */
@Service
@Transactional
public class StockServiceImpl implements StockService {

	private static final Logger log = LoggerFactory.getLogger(StockServiceImpl.class);

	private final StockRepository stockRepository;

	// ----------------
	// - CONSTRUCTORS -
	// ----------------

	@Autowired
	public StockServiceImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	// -----------
	// - METHODS -
	// -----------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Stock get(final Long id) {
		log.debug("get(id={})", id);
		return stockRepository.findOne(id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Stock> getByIsin(final String isin) {
		log.debug("getByIsin(isin={})", isin);
		return stockRepository.findByIsin(isin);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Optional<Stock> getByCode(final String code) {
		log.debug("getByCode(code={})", code);
		return stockRepository.findByCode(code);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Stock> getAll() {
		log.debug("getAll()");
		return (List<Stock>) stockRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Stock save(Stock stock) {
		try {
			return stockRepository.save(stock);
		} catch (Exception ex) {
			throw new StockException("Unable to save stock : " + stock, ex);
		}
	}
}