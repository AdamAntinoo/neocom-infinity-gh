package org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client;

import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
		name = "authorization",
		url = "${orangebank.client.cards.url}",
		primary = false/*,
        configuration = InternalFeignClientConfiguration.class*/)
public interface AuthorizationFeignClientV1 {
	@GetMapping(path = "/validateAuthorizationToken/{code}/state/{state}")
	ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationToken( @PathVariable final String code,
	                                                                               @PathVariable final String state,
	                                                                               @PathVariable("dataSource") final Optional<String> dataSource );
}
