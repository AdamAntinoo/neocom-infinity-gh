package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.RequestType;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class NIB01ValidateAuthorizationToken extends SupportSteps {
	private static final String SUB ="sub";
	private NeoComWorld neocomWorld;
//	private AuthorizationFeignClientV1 authorizationFeignClient;

	@Autowired
	public NIB01ValidateAuthorizationToken( final ConverterContainer cucumberTableToRequestConverters,
	                                        final NeoComWorld neocomWorld/*,
	                                        final AuthorizationFeignClientV1 authorizationFeignClient*/ ) {
		super(cucumberTableToRequestConverters);
		this.neocomWorld = neocomWorld;
//		this.authorizationFeignClient = authorizationFeignClient;
	}

	@Given("the state field matches {string}")
	public void the_state_field_matches( final String stateValue ) {
		Assert.assertEquals(this.neocomWorld.getValidateAuthorizationTokenRequest().getState(),
		                    stateValue);
	}

	@Then("the {string} response contains a valid Credential")
	public void the_response_contains_a_valid_Credential( final String endpointName ) {
		final RequestType requestType = RequestType.from(endpointName);
		switch (requestType) {
			case VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME:
				Assert.assertNotNull(this.neocomWorld.getValidateAuthorizationTokenResponse().getCredential());
				// TODO - Validate the credential contents probably against a list of values.
				break;
		}
	}
		@Then("the JWT generated token has the next contents")
	public void the_JWT_generated_token_has_the_next_contents(final List<Map<String, String>> dataTable) {
			final Map<String, String> row = dataTable.get( 0 );
		Assert.assertEquals( row.get( SUB ) , this.extractClaim(SUB, this.neocomWorld.getJwtAuthorizationToken()));
	}
	private String extractClaim ( final String fieldName , final String token){
		final DecodedJWT jwtToken = JWT.require( Algorithm.HMAC512( SECRET.getBytes() ) )
				.build()
				.verify( token.replace( TOKEN_PREFIX, "" ) );
		if (this.validateSubject( token )) { // Check this is the subject we expect
			return new UsernamePasswordAuthenticationToken( jwtToken.getPayload(), null, new ArrayList<>() );
		}
		final String payloadBase64 = token;
		final String payloadString = new String( Base64.decodeBase64( token.getBytes() ) );
		final JwtPayload payload = jsonMapper.readValue( payloadString, JwtPayload.class );
		if (null == payload) throw new NeoComSBException( configuredError );

	}
}
