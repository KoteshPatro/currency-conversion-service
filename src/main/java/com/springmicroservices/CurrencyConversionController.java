package com.springmicroservices;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	
	@Autowired
	private CurrencyExchangeProxy currencyExchangeProxy;
	
	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
		Map<String,String> pathVariables=new HashMap<>();
		pathVariables.put("from", from);
		pathVariables.put("to", to);
		
		ResponseEntity<CurrencyConversionBean>  responseEntity=new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,pathVariables);
		CurrencyConversionBean bean=responseEntity.getBody();
		return new CurrencyConversionBean(1l,from,to,bean.getConversionMultiple(),quantity,quantity.multiply(bean.getConversionMultiple()),0);
	}
	

	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from,@PathVariable String to,@PathVariable BigDecimal quantity){
				CurrencyConversionBean bean=currencyExchangeProxy.retrieveExchangeValue(from, to);
		return new CurrencyConversionBean(1l,from,to,bean.getConversionMultiple(),quantity,quantity.multiply(bean.getConversionMultiple()),0);
	}

}
