package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.RequestType;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.cucumber.datatable.DataTable;

public class NIB01ValidateAuthorizationToken extends SupportSteps {
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
	//	@Then("the JWT generated token has the next contents")
	public void the_JWT_generated_token_has_the_next_contents(final DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		throw new PendingException();
	}
}
