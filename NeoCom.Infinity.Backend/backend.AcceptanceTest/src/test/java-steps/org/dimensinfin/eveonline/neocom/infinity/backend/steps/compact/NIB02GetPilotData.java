package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;

import cucumber.api.PendingException;
import io.cucumber.datatable.DataTable;

public class NIB02GetPilotData extends SupportSteps {
	private NeoComWorld neocomWorld;

	@Autowired
	public NIB02GetPilotData( final ConverterContainer cucumberTableToRequestConverters,
	                          final NeoComWorld neocomWorld ) {
		super( cucumberTableToRequestConverters );
		this.neocomWorld = neocomWorld;
	}
//	@Then("the response has a pilot block with the next data")
	public void the_response_has_a_pilot_block_with_the_next_data( DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		// For other transformations you can register a DataTableType.
		throw new PendingException();
	}
//	@Given("{string} authorization token")
//	public void authorization_token( String jwtToken ) {
//		// Process special values
//		if ( jwtToken.equalsIgnoreCase( "<null>" )){
//			this.neocomWorld.setJwtAuthorizationToken( null );
//			return;
//		}
//		this.neocomWorld.setJwtAuthorizationToken( jwtToken );
//	}

//	@Then("the exception message is {string}")
//	public void the_exception_message_is( String exceptionMessage ) {
//		final ResponseEntity<CorporationDataResponse> response = this.neocomWorld.getCorporationDataResponse();
//	}
}