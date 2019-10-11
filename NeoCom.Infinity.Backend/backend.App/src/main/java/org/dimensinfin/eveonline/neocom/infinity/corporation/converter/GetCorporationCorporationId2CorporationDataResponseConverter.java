package org.dimensinfin.eveonline.neocom.infinity.corporation.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdOk;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCorporationsCorporationIdOk;
import org.dimensinfin.eveonline.neocom.infinity.adapter.ESIDataAdapterWrapper;
import org.dimensinfin.eveonline.neocom.infinity.corporation.rest.dto.CorporationDataResponse;

@Component
public class GetCorporationCorporationId2CorporationDataResponseConverter implements Converter<GetCorporationsCorporationIdOk, CorporationDataResponse> {
	private ESIDataAdapterWrapper esiDataAdapter;

	@Autowired
	public GetCorporationCorporationId2CorporationDataResponseConverter( final ESIDataAdapterWrapper esiDataAdapter ) {
		this.esiDataAdapter = esiDataAdapter;
	}

	@Override
	public CorporationDataResponse convert( final GetCorporationsCorporationIdOk corporationData ) {
		final GetCharactersCharacterIdOk ceoPilotData = this.esiDataAdapter
				.getCharactersCharacterId( corporationData.getCeoId() );
		return new CorporationDataResponse.Builder()
				.withCeoId( corporationData.getCeoId() )
				.withCeoPilotData( ceoPilotData )
				.withName( corporationData.getName() )
				.withDescription( corporationData.getDescription() )
				.withMemberCount( corporationData.getMemberCount() )
				.build();
	}
}
