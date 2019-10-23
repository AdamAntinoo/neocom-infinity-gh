package org.dimensinfin.eveonline.neocom.infinity.authorization;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComController;

@RestController
@CrossOrigin()
@Validated
@RequestMapping("/api/v1/neocom")
public class AuthorizationController extends NeoComController {
	private final AuthorizationService authorizationService;

	@Autowired
	public AuthorizationController( final AuthorizationService authorizationService ) {
		this.authorizationService = authorizationService;
	}

	@GetMapping(path = { "/validateAuthorizationToken" },
			produces = "application/json",
			consumes = "application/json")
	public ResponseEntity<ValidateAuthorizationTokenResponse> validate(
			@RequestParam(value = "code", required = true) final String code,
			@RequestParam(value = "state", required = true) final String state,
			@RequestParam(value = "dataSource", required = false) final Optional<String> dataSource ) {
		final ValidateAuthorizationTokenRequest.Builder requestBuilder = new ValidateAuthorizationTokenRequest.Builder()
				.withCode( code )
				.withState( state );
		if (dataSource.isPresent()) requestBuilder.optionalDataSource( dataSource.get() );
		return this.authorizationService.validateAuthorizationToken( requestBuilder.build() );
	}
}
