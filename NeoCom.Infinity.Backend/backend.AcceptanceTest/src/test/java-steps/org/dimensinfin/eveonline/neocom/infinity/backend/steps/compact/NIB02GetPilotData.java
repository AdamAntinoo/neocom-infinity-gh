package org.dimensinfin.eveonline.neocom.infinity.backend.steps.compact;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComWorld;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.SupportSteps;
import org.dimensinfin.eveonline.neocom.infinity.backend.test.support.ConverterContainer;
import org.dimensinfin.eveonline.neocom.infinity.pilot.client.v1.PilotDataResponse;

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
		final PilotDataResponse pilotData = this.neocomWorld
				.getPilotDataResponse().getBody();
		Assert.assertEquals( Integer.valueOf( row.get( PILOT_ID ) ).intValue(), pilotData.getId() );
		Assert.assertEquals( Integer.valueOf( row.get( CORPORATION_ID ) ).intValue(), pilotData.getCorporationId() );
		Assert.assertEquals( row.get( NAME ), pilotData.getName() );
	}
}