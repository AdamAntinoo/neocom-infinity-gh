package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.AuthorizationFeignClientV1;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.corporation.rest.v1.CorporationDataResponse;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.corporation.rest.v1.CorporationFeignClientV1;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.pilot.PilotDataResponse;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.pilot.PilotFeignClientV1;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.CucumberTableConverter;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.RequestType;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class NIBCommonSteps extends SupportSteps {
	private static final String CORPORATION_ID = "corporationId";
	private static final String PILOT_ID = "pilotId";

	private NeoComWorld neocomWorld;
	private AuthorizationFeignClientV1 authorizationFeignClient;
	private CorporationFeignClientV1 corporationFeignClient;
	private PilotFeignClientV1 pilotFeignClient;

	@Autowired
	public NIBCommonSteps( final ConverterContainer cucumberTableToRequestConverters,
	                       final NeoComWorld neocomWorld,
	                       final AuthorizationFeignClientV1 authorizationFeignClient,
	                       final CorporationFeignClientV1 corporationFeignClient,
	                       final PilotFeignClientV1 pilotFeignClient) {
		super( cucumberTableToRequestConverters );
		this.neocomWorld = neocomWorld;
		this.authorizationFeignClient = authorizationFeignClient;
		this.corporationFeignClient = corporationFeignClient;
		this.pilotFeignClient=pilotFeignClient;
	}

	@Given("a request to the {string} endpoint with the next data")
	public void a_request_to_the_endpoint_with_the_next_data( final String endpointName,
	                                                          final List<Map<String, String>> cucumberTable ) {
		final RequestType requestType = RequestType.from( endpointName );
		switch (requestType) {
			case VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME:
				final CucumberTableConverter cucumberTableConverter = this.findConverter( requestType );
				this.neocomWorld.setValidateAuthorizationTokenRequest(
						(ValidateAuthorizationTokenRequest) cucumberTableConverter.convert( cucumberTable.get( 0 ) )
				);
				break;
			case GET_CORPORATION_ENDPOINT_NAME:
				final Map<String, String> row = cucumberTable.get( 0 );
				final Integer corporationId = Integer.valueOf( row.get( CORPORATION_ID ) );
				Assert.assertNotNull( corporationId );
				this.neocomWorld.setCorporationIdentifier( corporationId );
				break;
			case GET_PILOT_ENDPOINT_NAME:
				final Map<String, String> rowPilot = cucumberTable.get( 0 );
				final Integer pilotId = Integer.valueOf( rowPilot.get( PILOT_ID ) );
				Assert.assertNotNull( pilotId );
				this.neocomWorld.setPilotIdentifier( pilotId );
				break;
			default:
				throw new NotImplementedException( "Request type not implemented." );
		}
	}

	@When("the {string} request is processed")
	public void the_request_is_processed( final String endpointName ) {
//		this.process( endpointName );
//	}
//
//	private void process( final String endpointName ) {
		final RequestType requestType = RequestType.from( endpointName );
		final ResponseEntity responseEntity = processRequest( requestType );
//		this.neocomWorld.setResponseEntity( responseEntity );
		this.neocomWorld.setHttpStatusCodeValue( responseEntity.getStatusCodeValue() );
	}

	@Then("the response status code is {int}")
	public void the_response_status_code_is( final Integer httpStatusCodeValue ) {
		Assert.assertNotNull( this.neocomWorld.getHttpStatusCodeValue() );
		Assert.assertEquals( httpStatusCodeValue.intValue(), this.neocomWorld.getHttpStatusCodeValue() );
	}

	private ResponseEntity processRequest( final RequestType requestType ) {
		try {
			switch (requestType) {
				case VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME:
					final ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationTokenResponse =
							this.authorizationFeignClient.validateAuthorizationToken(
									this.neocomWorld.getValidateAuthorizationTokenRequest()
							);
					Assert.assertNotNull( validateAuthorizationTokenResponse.getBody() );
					this.neocomWorld.setValidateAuthorizationTokenResponse( validateAuthorizationTokenResponse.getBody() );
					return validateAuthorizationTokenResponse;
				case GET_CORPORATION_ENDPOINT_NAME:
					Assert.assertTrue( this.neocomWorld.getCorporationIdentifier().isPresent() );
					final ResponseEntity<CorporationDataResponse> corporationDataResponse =
							this.corporationFeignClient.getCorporationData(
									this.neocomWorld.getCorporationIdentifier().get(),
									this.neocomWorld.getJwtAuthorizationToken()
							);
					this.neocomWorld.setCorporationDataResponse( corporationDataResponse );
					return corporationDataResponse;
				case GET_PILOT_ENDPOINT_NAME:
					Assert.assertTrue( this.neocomWorld.getPilotIdentifier().isPresent() );
					final ResponseEntity<PilotDataResponse> pilotDataResponse =
							this.pilotFeignClient.getPilotData(
									this.neocomWorld.getPilotIdentifier().get(),
									this.neocomWorld.getJwtAuthorizationToken()
							);
					this.neocomWorld.setPilotDataResponse( pilotDataResponse );
					return pilotDataResponse;
				default:
					throw new NotImplementedException( "Request type not implemented." );
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}