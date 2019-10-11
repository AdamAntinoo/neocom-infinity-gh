package org.dimensinfin.eveonline.neocom.infinity.corporation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCorporationsCorporationIdOk;
import org.dimensinfin.eveonline.neocom.infinity.adapter.ESIDataAdapterWrapper;
import org.dimensinfin.eveonline.neocom.infinity.corporation.converter.GetCorporationCorporationId2CorporationDataResponseConverter;
import org.dimensinfin.eveonline.neocom.infinity.corporation.rest.dto.CorporationDataResponse;

@Service
public class CorporationService {
	private ESIDataAdapterWrapper esiDataAdapter;
	private GetCorporationCorporationId2CorporationDataResponseConverter corporationDataConverter;

	@Autowired
	public CorporationService( final ESIDataAdapterWrapper esiDataAdapter,
	                           final GetCorporationCorporationId2CorporationDataResponseConverter corporationDataConverter ) {
		this.esiDataAdapter = esiDataAdapter;
		this.corporationDataConverter = corporationDataConverter;
	}

	public ResponseEntity<CorporationDataResponse> getCorporationData( final int corporationId ) {
		final GetCorporationsCorporationIdOk corporationData = this.esiDataAdapter.getCorporationsCorporationId( corporationId );
		if (null == corporationData)
		return new ResponseEntity<>( HttpStatus.NOT_FOUND );
		return new ResponseEntity<CorporationDataResponse>( this.corporationDataConverter.convert( corporationData ),
				HttpStatus.OK );
	}
}