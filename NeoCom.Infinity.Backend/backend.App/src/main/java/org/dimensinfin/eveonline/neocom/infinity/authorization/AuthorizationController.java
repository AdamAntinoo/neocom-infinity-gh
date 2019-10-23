package org.dimensinfin.eveonline.neocom.infinity.authorization;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComController;

@RestController
@CrossOrigin()
//@Validated
//@RequestMapping("/api/v1/neocom")
public class AuthorizationController extends NeoComController {
//	private final AuthorizationService authorizationService;
//
//	@Autowired
//	public AuthorizationController( final AuthorizationService authorizationService ) {
//		this.authorizationService = authorizationService;
//	}

	@GetMapping(path = { "/api/v1/neocom/validateAuthorizationToken" },
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<ValidateAuthorizationTokenResponse> validate(
			@RequestParam(value = "code", required = true) final String code,
			@RequestParam(value = "state", required = true) final String state,
			@RequestParam(value = "dataSource", required = false) final Optional<String> dataSource ) {
		final Credential credential = new Credential.Builder( 1234 )
				.withAccountId( 1234 )
				.withAccountName( "Test Account" )
				.build();
		final String jwtToken = "-POC-JWT-TOKEN-";
		return new ResponseEntity( new ValidateAuthorizationTokenResponse.Builder()
				.withCredential( credential )
				.withJwtToken( jwtToken )
				.build(), HttpStatus.OK );
	}
}
