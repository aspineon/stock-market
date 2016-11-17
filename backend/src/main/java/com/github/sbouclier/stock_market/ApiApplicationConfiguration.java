package com.github.sbouclier.stock_market;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Application configuration
 * 
 * @author Stéphane Bouclier
 *
 */
@Configuration
public class ApiApplicationConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**").allowedOrigins(CrossOrigin.DEFAULT_ORIGINS)
						.allowedHeaders(CrossOrigin.DEFAULT_ALLOWED_HEADERS)
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").maxAge(3600L);
			}
		};
	}
}