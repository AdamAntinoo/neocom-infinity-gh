package org.dimensinfin.eveonline.neocom.infinity.steps;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.infinity.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.support.pilot.rest.v1.PilotResponse;
import org.dimensinfin.eveonline.neocom.infinity.test.support.ConverterContainer;

import cucumber.api.java.en.Then;

public class NIB02GetPilotData extends SupportSteps {
	private static final String PILOT_ID = "pilotId";
	private static final String CORPORATION_ID = "corporationId";
	private static final String NAME = "name";
	private NeoComWorld neocomWorld;

	@Autowired
	public NIB02GetPilotData( final ConverterContainer cucumberTableToRequestConverters,
	                          final NeoComWorld neocomWorld ) {
		super( cucumberTableToRequestConverters );
		this.neocomWorld = neocomWorld;
	}

	@Then("the response has a pilot block with the next data")
	public void the_response_has_a_pilot_block_with_the_next_data( final List<Map<String, String>> dataTable ) {
		final Map<String, String> row = dataTable.get( 0 );
		final PilotResponse pilot = this.neocomWorld.getPilotResponseEntity().getBody();
		Assert.assertEquals( Integer.valueOf( row.get( PILOT_ID ) ).intValue(), pilot.getPilotId().intValue() );
		Assert.assertEquals( Integer.valueOf( row.get( CORPORATION_ID ) ).intValue(), pilot.getPilotId().intValue() );
		Assert.assertEquals( row.get( NAME ), pilot.getName() );
	}
}