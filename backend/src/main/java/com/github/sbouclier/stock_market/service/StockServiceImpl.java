package com.github.sbouclier.stock_market.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.sbouclier.stock_market.domain.Stock;
import com.github.sbouclier.stock_market.domain.StockHistoricalData;
import com.github.sbouclier.stock_market.exception.StockException;
import com.github.sbouclier.stock_market.exception.StockNotFoundException;
import com.github.sbouclier.stock_market.repository.StockHistoricalDataRepository;
import com.github.sbouclier.stock_market.repository.StockRepository;
import com.github.sbouclier.stock_market.service.YahooFinanceService.CSVData;

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

	private final StockHistoricalDataRepository stockHistoricalDataRepository;

	private final YahooFinanceService yahooFinanceService;

	// ----------------
	// - CONSTRUCTORS -
	// ----------------

	@Autowired
	public StockServiceImpl(YahooFinanceService yahooFinanceService, StockRepository stockRepository,
			StockHistoricalDataRepository stockHistoricalDataRepository) {
		this.yahooFinanceService = yahooFinanceService;
		this.stockRepository = stockRepository;
		this.stockHistoricalDataRepository = stockHistoricalDataRepository;
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
		log.debug("save(save={})", stock);

		try {
			return stockRepository.save(stock);
		} catch (Exception ex) {
			log.error(ex.getMessage());
			throw new StockException("Unable to save stock : " + stock, ex);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long countAll() {
		log.debug("countAll()");
		return stockRepository.count();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int loadHistoricalData(String isin, Date startDate, Date endDate) {
		log.debug("loadHistoricalData(isin={}, startDate={}, endDate={})", isin, startDate, endDate);

		// TODO check duplicate before
		
		final Stock stock = this.getByIsin(isin).orElseThrow(() -> new StockNotFoundException(isin));
		final List<StockHistoricalData> stockHistoricalData = new ArrayList<>();

		// load stock data from yahoo finance
		List<CSVData> csvDataList = yahooFinanceService.getStockData(stock.getCode(), startDate, endDate);

		// convert csv data to stock historical data
		StockHistoricalData shd = null;
		for (CSVData csvData : csvDataList) {
			shd = new StockHistoricalData(stock, csvData.getDate(), csvData.getOpen(), csvData.getClose(),
					csvData.getLow(), csvData.getHigh(), csvData.getAdjClose(), csvData.getVolume());
			stockHistoricalData.add(shd);
		}

		// save historical data
		// TODO optimize save
		log.debug("save stockHistoricalData, count = {}", stockHistoricalData.size());
		stockHistoricalDataRepository.save(stockHistoricalData);

		return stockHistoricalData.size();
	}
}