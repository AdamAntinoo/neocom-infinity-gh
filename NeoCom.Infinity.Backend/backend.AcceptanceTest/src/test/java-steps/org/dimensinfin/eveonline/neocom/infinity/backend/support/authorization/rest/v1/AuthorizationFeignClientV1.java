package org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.rest.v1;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.client.v1.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.client.v1.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.NeoComApiv1;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Component
public class AuthorizationFeignClientV1 {
	protected static Logger logger = LoggerFactory.getLogger( AuthorizationFeignClientV1.class );

	public ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationToken(
			final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest
	) throws IOException {
		final NeoComApiv1 serviceGetAccessToken = new Retrofit.Builder()
				.baseUrl( "http://localhost:9500" )
				.addConverterFactory( JacksonConverterFactory.create() )
				.build()
				.create( NeoComApiv1.class );
		final Call<ValidateAuthorizationTokenResponseClient> request = serviceGetAccessToken.validateAuthorizationToken(
				"application/json",
				validateAuthorizationTokenRequest.getCode(),
				validateAuthorizationTokenRequest.getState(),
				validateAuthorizationTokenRequest.getDataSourceName()
		);
		// Getting the request response to be stored if valid.
		final Response<ValidateAuthorizationTokenResponseClient> response = request.execute();
		if (response.isSuccessful()) {
			logger.info( "-- [AuthorizationService.getTokenTranslationResponse]> Response is 200 OK." );
			final ValidateAuthorizationTokenResponse auhorizationResponse = response.body().getBody();
			return new ResponseEntity<>( auhorizationResponse, HttpStatus.OK );
		} else return new ResponseEntity( HttpStatus.BAD_REQUEST );
	}
}
