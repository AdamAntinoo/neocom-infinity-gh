package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import java.io.IOException;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.AuthorizationFeignClientV1;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.ValidateAuthorizationTokenResponse;
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

	@Then("the response status code is {int}")
	public void the_response_status_code_is( final Integer httpStatusCodeValue ) {
		Assert.assertNotNull(this.neocomWorld.getHttpStatusCode());
		Assert.assertEquals(httpStatusCodeValue.intValue(), this.neocomWorld.getHttpStatusCode());
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
