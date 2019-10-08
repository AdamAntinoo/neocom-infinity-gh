package org.dimensinfin.eveonline.neocom.infinity.corporation.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCorporationsCorporationIdOk;
import org.dimensinfin.eveonline.neocom.infinity.corporation.rest.dto.CorporationDataResponse;

@Component
public class GetCorporationCorporationId2CorporationDataResponseConverter implements Converter<GetCorporationsCorporationIdOk, CorporationDataResponse> {
	@Override
	public CorporationDataResponse convert( final GetCorporationsCorporationIdOk corporationData ) {
		return new CorporationDataResponse.Builder()
				.withCeoId( corporationData.getCeoId() )
				.build();
	}
}
