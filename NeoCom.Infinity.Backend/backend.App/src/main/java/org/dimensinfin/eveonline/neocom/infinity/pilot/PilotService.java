package org.dimensinfin.eveonline.neocom.infinity.pilot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdOk;
import org.dimensinfin.eveonline.neocom.infinity.adapter.ESIDataAdapterWrapper;
import org.dimensinfin.eveonline.neocom.infinity.pilot.converter.GetCharactersCharacterId2PilotDataResponseConverter;
import org.dimensinfin.eveonline.neocom.infinity.pilot.client.v1.PilotDataResponse;

@Service
public class PilotService {
	private ESIDataAdapterWrapper esiDataAdapter;
	private GetCharactersCharacterId2PilotDataResponseConverter pilotDataConverter;

	@Autowired
	public PilotService( final ESIDataAdapterWrapper esiDataAdapter,
	                     final GetCharactersCharacterId2PilotDataResponseConverter pilotDataConverter ) {
		this.esiDataAdapter = esiDataAdapter;
		this.pilotDataConverter = pilotDataConverter;
	}

	public ResponseEntity<PilotDataResponse> getPilotData( final int pilotId ) {
		final GetCharactersCharacterIdOk pilotData = this.esiDataAdapter.getCharactersCharacterId( pilotId );
		if (null == pilotData)
			return new ResponseEntity<>( HttpStatus.NOT_FOUND );
		return new ResponseEntity<PilotDataResponse>( this.pilotDataConverter.convert( pilotData ).setPilotId(pilotId),
				HttpStatus.OK );
	}
}