package org.awesley.tests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Feign;
import feign.Request.Options;
//import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
//import feign.gson.GsonDecoder;
//import feign.gson.GsonEncoder;
//import feign.jackson.JacksonDecoder;
//import feign.jackson.JacksonEncoder;
//import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;

@Configuration
public class GenericJsonNodeClientConfiguration {

	private String serverURL = "https://jsonplaceholder.typicode.com";
	
	@Autowired
	protected ObjectMapper objectMapper;
	
//	@Autowired
//	protected Collection<RequestInterceptor> requestInterceptors;
	
	@Bean(name="genericJsonNodeClient")
	public IGenericJsonNodeClient genericJsonNodeClient() {
		IGenericJsonNodeClient genericJsonNodeClient = Feign.builder()
				//.requestInterceptors(requestInterceptors)
				.encoder(new JacksonEncoder(objectMapper))
				.decoder(new JacksonDecoder(objectMapper))
				.contract(new SpringMvcContract())
				.options(new Options(10000, 60000))  
				.logger(new Slf4jLogger(IGenericJsonNodeClient.class))
				  //.logLevel(Logger.Level.FULL)
				.target(IGenericJsonNodeClient.class, serverURL);
		return genericJsonNodeClient;
	}
}
