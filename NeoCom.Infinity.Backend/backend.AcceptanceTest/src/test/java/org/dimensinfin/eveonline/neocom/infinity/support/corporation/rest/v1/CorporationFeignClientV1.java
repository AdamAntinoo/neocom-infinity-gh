package org.dimensinfin.eveonline.neocom.infinity.support.corporation.rest.v1;

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
public class CorporationFeignClientV1 {
	public ResponseEntity<CorporationResponse> getCorporationData( final Integer corporationIdentifier
			, final String authorizationToken ) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule( new JodaModule() );
		final NeoComApiv1 serviceGetAccessToken = new Retrofit.Builder()
				.baseUrl( NeoComApiv1.NEOCOM_BACKEND_APP_HOST )
				.addConverterFactory( JacksonConverterFactory.create(mapper) )
				.build()
				.create( NeoComApiv1.class );
		final Call<CorporationResponse> request = serviceGetAccessToken.getCorporationData(
				"application/json",
				authorizationToken,
				corporationIdentifier
		);
		final Response<CorporationResponse> response = request.execute();
		if (response.isSuccessful()) {
			final CorporationResponse corporationResponse = response.body();
			return new ResponseEntity<>( corporationResponse, HttpStatus.OK );
		} else return new ResponseEntity( HttpStatus.valueOf( response.code() ) );
	}

}