package org.dimensinfin.eveonline.neocom.infinity.pilot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.dimensinfin.eveonline.neocom.domain.Pilot;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdOk;
import org.dimensinfin.eveonline.neocom.infinity.adapter.ESIDataAdapterWrapper;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.ErrorInfo;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.NeoComNotFoundException;

@Service
public class PilotService {
	private ESIDataAdapterWrapper esiDataAdapter;

	@Autowired
	public PilotService( final ESIDataAdapterWrapper esiDataAdapter ) {
		this.esiDataAdapter = esiDataAdapter;
	}

	public ResponseEntity<Pilot> getPilotData( final int pilotId ) {
		final GetCharactersCharacterIdOk pilotData = this.esiDataAdapter.getCharactersCharacterId( pilotId );
		if (null == pilotData)
			throw new NeoComNotFoundException( ErrorInfo.TARGET_NOT_FOUND, "Pilot", Integer.valueOf( pilotId).toString() );
		return new ResponseEntity<Pilot>( this.obtainPilotData( pilotId ), HttpStatus.OK );
	}

	public Pilot obtainPilotData( final int pilotId ) {
		// Access the rest of the pilot's esi data from the service.
		final GetCharactersCharacterIdOk pilotData = this.esiDataAdapter.getCharactersCharacterId( pilotId );
		final Pilot pilot = new Pilot.Builder()
				.withPilotIdentifier( pilotId )
				.withCharacterPublicData( pilotData )
				.withRaceData( this.esiDataAdapter.searchSDERace( pilotData.getRaceId() ) )
				.withAncestryData( this.esiDataAdapter.searchSDEAncestry( pilotData.getAncestryId() ) )
				.withBloodlineData( this.esiDataAdapter.searchSDEBloodline( pilotData.getBloodlineId() ) )
				.build();
		return pilot;
	}
}