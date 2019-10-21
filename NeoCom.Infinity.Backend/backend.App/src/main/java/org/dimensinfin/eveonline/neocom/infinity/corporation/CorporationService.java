package org.dimensinfin.eveonline.neocom.infinity.corporation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.dimensinfin.eveonline.neocom.domain.Corporation;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCorporationsCorporationIdOk;
import org.dimensinfin.eveonline.neocom.infinity.adapter.ESIDataAdapterWrapper;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.ErrorInfo;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.NeoComNotFoundException;
import org.dimensinfin.eveonline.neocom.infinity.pilot.PilotService;

@Service
public class CorporationService {
	private ESIDataAdapterWrapper esiDataAdapter;
	private final PilotService pilotService;

	@Autowired
	public CorporationService( final ESIDataAdapterWrapper esiDataAdapter,
	                           final PilotService pilotService ) {
		this.esiDataAdapter = esiDataAdapter;
		this.pilotService = pilotService;
	}

	public ResponseEntity<Corporation> getCorporationData( final int corporationId ) {
		final GetCorporationsCorporationIdOk corporationData = this.esiDataAdapter.getCorporationsCorporationId( corporationId );
		if (null == corporationData)
			throw new NeoComNotFoundException( ErrorInfo.TARGET_NOT_FOUND, "Pilot", Integer.valueOf( corporationId ).toString() );
		return new ResponseEntity<Corporation>( this.obtainCorporationData( corporationId ), HttpStatus.OK );
	}

	public Corporation obtainCorporationData( final int corporationId ) {
		final GetCorporationsCorporationIdOk corporationData = this.esiDataAdapter.getCorporationsCorporationId( corporationId );
		// Access the rest of the corporation's esi data from the service.
		final Corporation.Builder corporationBuilder = new Corporation.Builder()
				.withCorporationId( corporationId )
				.withCorporationPublicData( corporationData )
				.withCeoPilotData( this.pilotService.obtainPilotData( corporationData.getCeoId() ) );
		if ( null != corporationData.getAllianceId())
			return corporationBuilder.optionslAlliance(
					this.esiDataAdapter.getAlliancesAllianceId( corporationData.getAllianceId() ) )
					.build();
		else return corporationBuilder.build();
	}
}