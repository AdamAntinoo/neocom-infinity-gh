package org.dimensinfin.eveonline.neocom.infinity.authorization.rest;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import org.dimensinfin.eveonline.neocom.infinity.NeoComInfinityBackendApplication;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = NeoComInfinityBackendApplication.class)
@WebAppConfiguration
public class LoginControllerTest {
	protected MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	@Before
	protected void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	@Test
	public void requestComplete() throws Exception {
		String endpoint = "/validateAuthorizationToken?code=-TEST-CODE-&state=-TEST-STATE-&datasource=singulatity";
		MvcResult mvcResult = mvc.perform( MockMvcRequestBuilders.get( endpoint )
				.accept( MediaType.APPLICATION_JSON_VALUE ) ).andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assert.assertEquals( 200, status );
		String content = mvcResult.getResponse().getContentAsString();
		ValidateAuthorizationTokenResponse response = this.mapFromJson( content, ValidateAuthorizationTokenResponse.class );
		Assert.assertNotNull( response );
		Assert.assertNotNull( response.getJwtToken() );
		Assert.assertNotNull( response.getCredential() );
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}
	protected <T> T mapFromJson( String json, Class<T> clazz )
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue( json, clazz );
	}
}