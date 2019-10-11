package org.dimensinfin.eveonline.neocom.infinity.backend.support.pilot;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComApiv1;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class PilotFeignClientV1 {
	public ResponseEntity<PilotDataResponse> getPilotData( final Integer pilotIdentifier,
	                                                             final String authorizationToken ) throws IOException {
		final NeoComApiv1 serviceGetAccessToken = new Retrofit.Builder()
				.baseUrl( NeoComApiv1.NEOCOM_BACKEND_APP_HOST )
				.addConverterFactory( JacksonConverterFactory.create() )
				.build()
				.create( NeoComApiv1.class );
		final Call<PilotDataResponse> request = serviceGetAccessToken.getPilotData(
				"application/json",
				authorizationToken,
				pilotIdentifier
		);
		final Response<PilotDataResponse> response = request.execute();
		if (response.isSuccessful()) {
			final PilotDataResponse pilotDataResponse = response.body();
			return new ResponseEntity<PilotDataResponse>( pilotDataResponse, HttpStatus.OK );
		} else return new ResponseEntity( HttpStatus.valueOf( response.code() ) );
	}
}