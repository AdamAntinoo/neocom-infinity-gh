package org.dimensinfin.poc.backend;

import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;

@RestController
@CrossOrigin()
public class AuthorizationController extends NeoComController {
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
