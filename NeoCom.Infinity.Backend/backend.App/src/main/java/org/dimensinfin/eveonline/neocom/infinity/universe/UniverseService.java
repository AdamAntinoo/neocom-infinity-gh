package org.dimensinfin.eveonline.neocom.infinity.universe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.dimensinfin.eveonline.neocom.infinity.adapter.ESIDataAdapterWrapper;
import org.dimensinfin.eveonline.neocom.infinity.universe.client.v1.ServerStatus;

@Service
public class UniverseService {
	private ESIDataAdapterWrapper esiDataAdapter;

	@Autowired
	public UniverseService( final ESIDataAdapterWrapper esiDataAdapter ) {
		this.esiDataAdapter = esiDataAdapter;
	}

	public ResponseEntity<ServerStatus> getServerStatus( final String server ) {
		return new ResponseEntity<ServerStatus>( new ServerStatus.Builder()
				.withServer( server )
				.withStatus( this.esiDataAdapter.getUniverseStatus( server ) )
				.build(), HttpStatus.OK );
	}
}