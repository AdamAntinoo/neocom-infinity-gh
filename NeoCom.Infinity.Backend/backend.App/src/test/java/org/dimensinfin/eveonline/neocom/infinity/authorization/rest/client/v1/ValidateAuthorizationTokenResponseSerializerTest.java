package org.dimensinfin.eveonline.neocom.infinity.authorization.rest.client.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;

//@JsonTest
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ValidateAuthorizationTokenResponseSerializerTest {
	private static final String JWT_TOKEN = "-TEST-JWT-TOKEN-";

	@Autowired
	private ObjectMapper objectMapper;

//	@Test
	public void serialize() throws JsonProcessingException {
		final Credential credential = new Credential.Builder( 123456 )
				.withAccountId( 123456 )
				.withAccountName( "-TESTING-ACCOUNT-NAME-" )
				.withDataSource( "Tranquility" )
				.withAssetsCount( 987 )
				.withWalletBalance( 876.0 )
				.withMiningResourcesEstimatedValue( 765.0 )
				.withRaceName( "-TEST-RACE-NAME-" )
				.build();
		final ValidateAuthorizationTokenResponse validateAuthorizationTokenResponse = new ValidateAuthorizationTokenResponse.Builder()
				.withJwtToken( JWT_TOKEN )
				.withCredential( credential )
				.build();
		final String expected = "";
		String obtained = objectMapper.writeValueAsString( validateAuthorizationTokenResponse );
		Assert.assertEquals( expected, obtained );
	}
}
