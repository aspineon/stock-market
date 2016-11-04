package com.github.sbouclier.stock_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.sbouclier.stock_market.domain.StockHistoricalData;

/**
 * Stock historical data repository
 * 
 * @author Stéphane Bouclier
 *
 */
public interface StockHistoricalDataRepository extends JpaRepository<StockHistoricalData, Long> {

}