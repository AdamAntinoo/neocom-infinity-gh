package org.dimensinfin.eveonline.neocom.infinity.backend.support.corporation.rest.v1;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComApiv1;
import org.dimensinfin.eveonline.neocom.infinity.corporation.rest.client.v1.CorporationDataResponse;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class CorporationFeignClientV1 {
	public ResponseEntity<CorporationDataResponse> getCorporationData( final Integer corporationIdentifier
			, final String authorizationToken ) throws IOException {
		final NeoComApiv1 serviceGetAccessToken = new Retrofit.Builder()
				.baseUrl( NeoComApiv1.NEOCOM_BACKEND_APP_HOST )
				.addConverterFactory( JacksonConverterFactory.create() )
				.build()
				.create( NeoComApiv1.class );
		final Call<CorporationDataResponse> request = serviceGetAccessToken.getCorporationData(
				"application/json",
				authorizationToken,
				corporationIdentifier
		);
		final Response<CorporationDataResponse> response = request.execute();
		if (response.isSuccessful()) {
			final CorporationDataResponse corporationResponse = response.body();
			return new ResponseEntity<>( corporationResponse, HttpStatus.OK );
		} else return new ResponseEntity( HttpStatus.valueOf( response.code() ) );
	}

}