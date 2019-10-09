package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.AuthorizationFeignClientV1;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.RequestType;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class NIB01ValidateAuthorizationToken extends SupportSteps {
	private NeoComWorld neocomWorld;
	private AuthorizationFeignClientV1 authorizationFeignClient;

	@Autowired
	public NIB01ValidateAuthorizationToken( final ConverterContainer cucumberTableToRequestConverters,
	                                        final NeoComWorld neocomWorld,
	                                        final AuthorizationFeignClientV1 authorizationFeignClient ) {
		super(cucumberTableToRequestConverters);
		this.neocomWorld = neocomWorld;
		this.authorizationFeignClient = authorizationFeignClient;
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
}
