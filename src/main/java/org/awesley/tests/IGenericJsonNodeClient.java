package org.awesley.tests;

import javax.ws.rs.GET;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.JsonNode;

import feign.Headers;

//import feign.Headers;
//import feign.RequestLine;


@FeignClient(url="https://jsonplaceholder.typicode.com")
public interface IGenericJsonNodeClient {

	// @GetMapping(value = "/items",produces = MediaType.APPLICATION_JSON_VALUE)
	// @RequestLine(value = "GET /items")
	@RequestMapping(value = "/users/{userId}", method = RequestMethod.GET, consumes = { "application/json" }, produces = { "application/json" })
	@Headers({ "Content-Type: application/json", "Accepts: application/json" })
	@GET
	JsonNode getItem(@PathVariable("userId") String userId);
}
