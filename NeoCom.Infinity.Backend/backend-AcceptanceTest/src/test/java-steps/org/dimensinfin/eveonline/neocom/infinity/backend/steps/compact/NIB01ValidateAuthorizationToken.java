package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import org.apache.commons.lang.NotImplementedException;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.AuthorizationFeignClientV1;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.CucumberTableConverter;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.RequestType;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

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

	@Given("a request to the {string} endpoint with the next data")
	public void a_request_to_the_endpoint_with_the_next_data( final String endpointName,
	                                                          final List<Map<String, String>> cucumberTable ) {
		final RequestType requestType = RequestType.from(endpointName);
		final CucumberTableConverter cucumberTableConverter = this.findConverter(requestType);
		switch (requestType) {
			case VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME:
				this.neocomWorld.setValidateAuthorizationTokenRequest(
						(ValidateAuthorizationTokenRequest) cucumberTableConverter.convert(cucumberTable.get(0))
				);
				break;
			default:
				throw new NotImplementedException();
		}
	}

	@Given("the state field matches {string}")
	public void the_state_field_matches( final String stateValue ) {
		Assert.assertEquals(this.neocomWorld.getValidateAuthorizationTokenRequest().getState(),
		                    stateValue);
	}

	@When("the {string} request is processed")
	public void the_request_is_processed( final String endpointName ) {
		this.process(endpointName);
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


	private void process( final String endpointName ) {
//		try {
		final RequestType requestType = RequestType.from(endpointName);
		final ResponseEntity cardResponseEntity = process(requestType);
		this.neocomWorld.setHttpStatusCode(cardResponseEntity.getStatusCodeValue());
//		} catch (NeoComRuntimeException neocomException) {
//			this.neocomWorld.setHttpStatus(httpStatusConverter.convert(neocomException).value());
//			this.neocomWorld.setErrorInfo(neocomException.getErrorInfo());
//		}
	}

	private ResponseEntity process( final RequestType requestType ) {
		try {
			switch (requestType) {
				case VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME:
					ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationTokenResponse =
							null;
					validateAuthorizationTokenResponse = this.authorizationFeignClient.validateAuthorizationToken(
							this.neocomWorld.getValidateAuthorizationTokenRequest()
					);
					Assert.assertNotNull(validateAuthorizationTokenResponse.getBody());
					this.neocomWorld.setValidateAuthorizationTokenResponse(validateAuthorizationTokenResponse.getBody());
					return validateAuthorizationTokenResponse;
				default:
					throw new NotImplementedException();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
