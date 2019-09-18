package org.dimensinfin.eveonline.neocom.infinity.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@JsonTest
@RunWith(SpringRunner.class)
public class CredentialSerializerTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testSerialization() throws JsonProcessingException {
		Credential user = new Credential();
		String json = objectMapper.writeValueAsString(user);

		assertEquals("{\"favoriteColor\":\"#f0f8ff\"}", json);
	}
}
