package org.dimensinfin.eveonline.neocom.infinity.support.pilot.rest.v1;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.support.NeoComApiv1;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class PilotFeignClientV1 {
	public ResponseEntity<PilotResponse> getPilotData( final Integer pilotIdentifier,
	                                           final String authorizationToken ) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule( new JodaModule() );
		final NeoComApiv1 serviceGetAccessToken = new Retrofit.Builder()
				.baseUrl( NeoComApiv1.NEOCOM_BACKEND_APP_HOST )
				.addConverterFactory( JacksonConverterFactory.create(mapper) )
				.build()
				.create( NeoComApiv1.class );
		final Call<PilotResponse> request = serviceGetAccessToken.getPilotData(
				"application/json",
				authorizationToken,
				pilotIdentifier
		);
		final Response<PilotResponse> response = request.execute();
		if (response.isSuccessful()) {
			final PilotResponse body = response.body();
			return new ResponseEntity<PilotResponse>( body, HttpStatus.OK );
		} else return new ResponseEntity( HttpStatus.valueOf( response.code() ) );
	}
}