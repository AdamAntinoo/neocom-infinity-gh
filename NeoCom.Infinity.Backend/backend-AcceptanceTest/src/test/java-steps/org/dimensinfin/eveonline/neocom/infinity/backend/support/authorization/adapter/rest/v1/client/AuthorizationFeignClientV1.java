package org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
//@FeignClient(
//		name = "authorization",
//		url = "${orangebank.client.cards.url}",
//		primary = false/*,
//        configuration = InternalFeignClientConfiguration.class*/)
public class AuthorizationFeignClientV1 {
	private CloseableHttpClient httpClient = HttpClients.createDefault();

	public ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationToken(
			final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest
	) throws IOException {
		HttpGet request = new HttpGet("http://localhost:" + 9200 + "/api/v1/neocom/validateAuthorizationToken/123/state/456");
//		StringEntity entity = new StringEntity(jsonString);
		request.addHeader("content-type", "application/json");
//		request.setEntity(entity);
		HttpResponse response = httpClient.execute(request);
		final Credential credential = new Credential.Builder(123).build();
		return new ResponseEntity(new ValidateAuthorizationTokenResponse.Builder().withCredential(credential),
		                          HttpStatus.OK);
	}
//	@GetMapping(path = "/validateAuthorizationToken/{code}/state/{state}")
//	ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationToken( @PathVariable final String code,
//	                                                                               @PathVariable final String state,
//	                                                                               @PathVariable("dataSource") final Optional<String> dataSource );
}
