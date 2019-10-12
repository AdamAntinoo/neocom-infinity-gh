package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;

import cucumber.api.java.en.Given;

public class NIB02GetCorporationData extends SupportSteps {
	private NeoComWorld neocomWorld;
//	private AuthorizationFeignClientV1 authorizationFeignClient;

	@Autowired
	public NIB02GetCorporationData( final ConverterContainer cucumberTableToRequestConverters,
	                                final NeoComWorld neocomWorld/*,
	                                final AuthorizationFeignClientV1 authorizationFeignClient*/ ) {
		super( cucumberTableToRequestConverters );
		this.neocomWorld = neocomWorld;
//		this.authorizationFeignClient = authorizationFeignClient;
	}

	@Given("{string} authorization token")
	public void authorization_token( String jwtToken ) {
		// Process special values
		if ( jwtToken.equalsIgnoreCase( "<null>" )){
			this.neocomWorld.setJwtAuthorizationToken( null );
			return;
		}
		this.neocomWorld.setJwtAuthorizationToken( jwtToken );
	}

//	@Then("the exception message is {string}")
//	public void the_exception_message_is( String exceptionMessage ) {
//		final ResponseEntity<CorporationDataResponse> response = this.neocomWorld.getCorporationDataResponse();
//	}
}