package com.github.sbouclier.stock_market.service;

import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.sbouclier.stock_market.exception.StockException;

/**
 * Default {@link YahooFinanceService} implementation
 * 
 * @author Stéphane Bouclier
 *
 */
@Service
@Transactional
public class YahooFinanceServiceImpl implements YahooFinanceService {

	private static final Logger log = LoggerFactory.getLogger(YahooFinanceServiceImpl.class);

	@Value("${yahoo.finance.stocks.template-url}")
	private final String templateURL = "http://real-chart.finance.yahoo.com/table.csv?s=%s&a=%d&b=%d&c=%d&d=%d&e=%d&f=%d&g=d&ignore=.csv";

	private static final String[] FILE_HEADER = { "Date", "Open", "High", "Low", "Close", "Volume", "Adj Close" };

	@Override
	public List<CSVData> getStockData(String code, Date startDate, Date endDate) throws StockException {
		log.debug("getStockData(code={}, startDate={}, endDate={})", code, startDate, endDate);

		final String strUrl = buildUrl(code, startDate, endDate);

		final URL url;
		try {
			url = new URL(strUrl);
		} catch (MalformedURLException ex) {
			throw new StockException("Unable to open stream : " + strUrl, ex);
		}

		try (Reader reader = new InputStreamReader(url.openStream(), "UTF-8");
				CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader(FILE_HEADER))) {
			log.info("Stream opened on {}", strUrl);

			return parseCSVRecord(parser.getRecords());
		} catch (Exception ex) {
			throw new StockException("Unable to load file : " + strUrl, ex);
		}
	}

	/**
	 * Build url from criterias
	 * 
	 * @param code
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private String buildUrl(String code, Date startDate, Date endDate) {
		log.debug("buildUrl(code={}, startDate={}, endDate={})", code, startDate, endDate);

		final LocalDate ldSart = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		final LocalDate ldEnd = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		return String.format(templateURL, code, (ldSart.getMonthValue() - 1), ldSart.getDayOfMonth(), ldSart.getYear(),
				(ldEnd.getMonthValue() - 1), ldEnd.getDayOfMonth(), ldEnd.getYear());
	}

	/**
	 * Convert CSVRecord list to CSVData list
	 * 
	 * @param csvRecords
	 * @return
	 */
	private List<CSVData> parseCSVRecord(List<CSVRecord> csvRecords) {
		log.debug("parseCSVRecord(csvRecords={})", csvRecords);

		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
		final List<CSVData> data = new ArrayList<>();

		String date = null;
		String open = null;
		String high = null;
		String low = null;
		String close = null;
		String volume = null;
		String adjClose = null;

		// skip header
		for (int i = 1; i < csvRecords.size(); i++) {
			final CSVRecord record = csvRecords.get(i);

			date = record.get(FILE_HEADER[0]);
			open = record.get(FILE_HEADER[1]);
			high = record.get(FILE_HEADER[2]);
			low = record.get(FILE_HEADER[3]);
			close = record.get(FILE_HEADER[4]);
			volume = record.get(FILE_HEADER[5]);
			adjClose = record.get(FILE_HEADER[6]);

			Date dateRecord;
			try {
				dateRecord = formatter.parse(date);

				data.add(new CSVData(dateRecord, new BigDecimal(open), new BigDecimal(close), new BigDecimal(low),
						new BigDecimal(high), new BigDecimal(adjClose), Long.valueOf(volume)));
			} catch (ParseException ex) {
				throw new StockException("Unable to parse date from csv file line " + i, ex);
			}
		}

		return data;
	}
}