package org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dimensinfin.eveonline.neocom.auth.TokenRequestBody;
import org.dimensinfin.eveonline.neocom.auth.TokenTranslationResponse;
import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

@Component
//@FeignClient(
//		name = "authorization",
//		url = "${orangebank.client.cards.url}",
//		primary = false/*,
//        configuration = InternalFeignClientConfiguration.class*/)
public class AuthorizationFeignClientV1 {
	protected static Logger logger = LoggerFactory.getLogger(AuthorizationFeignClientV1.class);

	private CloseableHttpClient httpClient = HttpClients.createDefault();

	public ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationToken(
			final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest
	) throws IOException {
		HttpGet request = new HttpGet("http://localhost:" + 9200 + "/api/v1/neocom/validateAuthorizationToken/123/state/456");
//		StringEntity entity = new StringEntity(jsonString);
		request.addHeader("content-type", "application/json");
//		request.setEntity(entity);
		HttpResponse response = httpClient.execute(request);
//		final Credential credential = new Credential.Builder(123).build();
//		credential.set
//				                              .withAccountId(123)
//				                              .withAccountName("ww")
//				                              .build();
//		return new ResponseEntity(new ValidateAuthorizationTokenResponse.Builder().withCredential(credential),
//		                          HttpStatus.OK);
		return new ResponseEntity<>(this.alternateClient(validateAuthorizationTokenRequest),HttpStatus.OK);
	}

	//	@GetMapping(path = "/validateAuthorizationToken/{code}/state/{state}")
//	ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationToken( @PathVariable final String code,
//	                                                                               @PathVariable final String state,
//	                                                                               @PathVariable("dataSource") final Optional<String> dataSource );
	public ValidateAuthorizationTokenResponse alternateClient(final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest) throws IOException {
		final NeoComApiv1 serviceGetAccessToken = new Retrofit.Builder()
				                                             .baseUrl("http://localhost:" + 9200)
				                                             .addConverterFactory(JacksonConverterFactory.create())
				                                             .build()
				                                             .create(NeoComApiv1.class);
		final Call<ValidateAuthorizationTokenResponse> request = serviceGetAccessToken.validateAuthorizationToken(
				"application/json",
				validateAuthorizationTokenRequest.getCode(),
				validateAuthorizationTokenRequest.getState(),
				validateAuthorizationTokenRequest.getDataSourceName()
		);
		// Getting the request response to be stored if valid.
//		try {
			final Response<ValidateAuthorizationTokenResponse> response = request.execute();
			if (response.isSuccessful()) {
				logger.info("-- [AuthorizationService.getTokenTranslationResponse]> Response is 200 OK.");
				final ValidateAuthorizationTokenResponse auhorizationResponse = response.body();
				return auhorizationResponse;
			} else return null;
//		}
	}
}
