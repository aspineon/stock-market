package com.github.sbouclier.stock_market.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.github.sbouclier.stock_market.domain.Stock;

/**
 * Stock repository
 * 
 * @author Stéphane Bouclier
 *
 */
public interface StockRepository extends JpaRepository<Stock, Long> {
	Optional<Stock> findByIsin(String isin);
	Optional<Stock> findByCode(String code);
}