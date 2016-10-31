package com.github.sbouclier.stock_market.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import javax.transaction.Transactional;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.context.WebApplicationContext;

import com.github.sbouclier.stock_market.ApiApplication;
import com.github.sbouclier.stock_market.domain.Stock;

/**
 * Tests for {@link StockRestController}
 * 
 * @author Stéphane Bouclier
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApiApplication.class)
@WebAppConfiguration
@Transactional
public class StockRestControllerTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	@SuppressWarnings("rawtypes")
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();
		Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@SuppressWarnings("unchecked")
	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	// ---------- create stock ----------

	@Test
	public void createStock() throws Exception {
		String stockJson = json(new Stock("ABC123456789", "NEW", "New"));
		this.mockMvc.perform(post("/api/stocks").contentType(contentType).content(stockJson))
				.andExpect(status().isCreated());
	}

	@Test
	public void createStockWithBadIsin() throws Exception {
		String stockJson = json(new Stock("123456", "NEW", "New"));
		this.mockMvc.perform(post("/api/stocks").contentType(contentType).content(stockJson))
				.andExpect(status().isInternalServerError()).andExpect(jsonPath("$[0].logref", is("error")))
				.andExpect(jsonPath("$[0].message", containsString("Unable to save stock : Stock")))
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void createDuplicateStock() throws Exception {
		String stockJson = json(new Stock("ABC123456789", "NEW", "New"));
		this.mockMvc.perform(post("/api/stocks").contentType(contentType).content(stockJson))
				.andExpect(status().isCreated());

		this.mockMvc.perform(post("/api/stocks").contentType(contentType).content(stockJson))
				.andExpect(status().isInternalServerError());
	}

	// ---------- read stock ----------

	@Test
	public void readStockWithValidCode() throws Exception {
		mockMvc.perform(get("/api/stocks/US0378331005")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.isin", is("US0378331005"))).andExpect(jsonPath("$.code", is("AAPL")))
				.andExpect(jsonPath("$.name", is("Apple")));
	}

	@Test
	public void readStockWithInvalidCode() throws Exception {
		mockMvc.perform(get("/api/stocks/UNKNOWN")).andExpect(status().isNotFound());
	}

	// ---------- read stocks ----------

	@Test
	public void readStocks() throws Exception {
		mockMvc.perform(get("/api/stocks")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(3)))
				.andExpect(jsonPath("$[0].code", is("AAPL"))).andExpect(jsonPath("$[1].code", is("GOOGL")))
				.andExpect(jsonPath("$[2].code", is("MSFT")));
	}
}