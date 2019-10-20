package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ResponseType;

import cucumber.api.java.en.Then;

public class NIB04ValidateAuthorizationTokenContract extends SupportSteps {
	private static final String RESPONSE_TYPE = "responseType";
	private static final String JWT_TOKEN = "jwtToken";
	private static final String CREDENTIAL = "credential";
	private NeoComWorld neocomWorld;

	@Autowired
	public NIB04ValidateAuthorizationTokenContract(
			final ConverterContainer cucumberTableToRequestConverters,
			final NeoComWorld neocomWorld ) {
		super( cucumberTableToRequestConverters );
		this.neocomWorld = neocomWorld;
	}

	@Then("the {string} has the next contents")
	public void the_has_the_next_contents( final String responseTypeName, final List<Map<String, String>> dataTable ) {
		final ResponseType responseType = ResponseType.from( responseTypeName );
		switch (responseType) {
			case VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME:
				final Map<String, String> row = dataTable.get( 0 );
				final ValidateAuthorizationTokenResponse response = this.neocomWorld.getValidateAuthorizationTokenResponse();
				Assert.assertEquals( row.get( RESPONSE_TYPE ), response.getResponseType() );
				Assert.assertEquals( row.get( JWT_TOKEN ), response.getJwtToken() );
				Assert.assertEquals( row.get( CREDENTIAL ), response.getCredential().getJsonClass() );
				break;
		}
	}
}