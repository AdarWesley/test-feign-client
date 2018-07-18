package org.awesley.tests;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Response;
import feign.codec.Decoder;
//import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonDecoder;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { TestFiegnClientApplication.class })
public class TestFiegnClientApplicationTests {

	@Autowired
	private IGenericJsonNodeClient genericJsonNodeClient;
	
	//@Autowired
	Decoder decoder = new JacksonDecoder(new ObjectMapper());
			// new ResponseEntityDecoder(new SpringDecoder(new DependecyObjectProvider()));
	
	@Test
	public void jacksonDecoder_shouldDeserializeToJsonNode() {
		String jsonString = 
			"{" + 
				"\"data\": {" + 
					"\"id\": 2," + 
					"\"first_name\": \"Janet\"," + 
					"\"last_name\": \"Weaver\"," + 
					"\"avatar\": \"https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg\"" + 
				"}" + 
			"}";
		
		Map<String, Collection<String>> headers = new HashMap<String, Collection<String>>();
		Response res = 
			Response.builder()
				.status(200)
				.reason("Ok")
				.headers((Map<String, Collection<String>>)headers)
				.body(jsonString, Charset.forName("UTF-8"))
				.build();
		
		JsonNode jobj = null;
		try {
			jobj = (JsonNode) decoder.decode(res, JsonNode.class);
			System.out.println(jobj.toString());
			System.out.println(jobj.at("/data/first_name").toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void genericJsonNodeClient_shouldRetrieveRESTResultAndDesrializeToJsonNode() {
		JsonNode jobj = genericJsonNodeClient.getItem("2");
		System.out.println(jobj.toString());
		System.out.println(jobj.at("/name").toString());
	}
}
