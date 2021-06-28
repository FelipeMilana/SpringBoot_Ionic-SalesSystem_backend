package com.projects.SalesSystem.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.SalesSystem.entities.dto.CashPaymentDTO;
import com.projects.SalesSystem.entities.dto.CashWithExchangePaymentDTO;
import com.projects.SalesSystem.entities.dto.ConsortiumPaymentDTO;
import com.projects.SalesSystem.entities.dto.ConsortiumWithExchangePaymentDTO;
import com.projects.SalesSystem.entities.dto.ExchangeWithCashbackPaymentDTO;
import com.projects.SalesSystem.entities.dto.FundedPaymentDTO;
import com.projects.SalesSystem.entities.dto.FundedWithExchangePaymentDTO;

@Configuration
public class JacksonConfig {
	
	// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-ofinterfaceclass-
	//without-hinting-the-pare
	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(CashPaymentDTO.class);
				objectMapper.registerSubtypes(CashWithExchangePaymentDTO.class);
				objectMapper.registerSubtypes(FundedPaymentDTO.class);
				objectMapper.registerSubtypes(FundedWithExchangePaymentDTO.class);
				objectMapper.registerSubtypes(ConsortiumPaymentDTO.class);
				objectMapper.registerSubtypes(ConsortiumWithExchangePaymentDTO.class);
				objectMapper.registerSubtypes(ExchangeWithCashbackPaymentDTO.class);
				super.configure(objectMapper);
			};
		};
		return builder;
	}
}
