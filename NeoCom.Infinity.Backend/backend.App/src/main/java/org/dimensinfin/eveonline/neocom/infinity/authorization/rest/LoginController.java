package org.dimensinfin.eveonline.neocom.infinity.authorization.rest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.infinity.authorization.AuthorizationService;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComController;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComSBException;

@RestController
@Validated
@RequestMapping("/api/v1/neocom")
public class LoginController extends NeoComController {
	private final AuthorizationService authorizationService;

	@Autowired
	public LoginController( final AuthorizationService authorizationService ) {
		this.authorizationService = authorizationService;
	}

	@GetMapping(path = { "/validateAuthorizationToken?code={code}&state={state}&datasource={datasource}" },
			produces = "application/json",
			consumes = "application/json")
	public ResponseEntity<ValidateAuthorizationTokenResponse> validate( @RequestParam(value = "code") final String code,
	                                @RequestParam(value = "state") final String state,
	                                @RequestParam(value = "dataSource", required = false) final Optional<String> dataSource ) {
		final ValidateAuthorizationTokenRequest.Builder requestBuilder = new ValidateAuthorizationTokenRequest.Builder()
				.withCode( code )
				.withState( state );
		if (dataSource.isPresent()) requestBuilder.optionalDataSource( dataSource.get() );
		return  this.authorizationService.validateAuthorizationToken( requestBuilder.build() );
	}

	@Deprecated
	@GetMapping(path = {
			"/validateAuthorizationToken/{code}/state/{state}",
			"/validateAuthorizationToken/{code}/state/{state}/datasource/{dataSource}" },
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationToken( @PathVariable final String code,
	                                                                                      @PathVariable final String state,
	                                                                                      @PathVariable("dataSource") final Optional<String> dataSource ) {
		if (dataSource.isPresent())
			logger.info( ">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/validateAuthorizationToken/{}/state/{}/datasource/{}",
					code, state, dataSource );
		else
			logger.info( ">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/validateAuthorizationToken/{}/state/{}", code, state );
		final ValidateAuthorizationTokenRequest.Builder requestBuilder = new ValidateAuthorizationTokenRequest.Builder()
				.withCode( code )
				.withState( state );
		if (dataSource.isPresent()) requestBuilder.optionalDataSource( dataSource.get() );
		return new ResponseEntity( this.authorizationService.validateAuthorizationToken( requestBuilder.build() ),
				HttpStatus.OK );
	}

	@ExceptionHandler(NeoComSBException.class)
	public ResponseEntity<NeoComSBException> handleNeoComSBException( final NeoComSBException neocomException ) {
		// Convert the exception to a json response
		HttpHeaders headers = new HttpHeaders();
		headers.add( "xApp-Error-Message", neocomException.getMessage() );
		headers.add( "xApp-Error-Class", neocomException.getExceptionType() );
		logger.info( "EX [LoginController.handleNeoComSBException]> Exception: {}", neocomException.getMessage() );
		return new ResponseEntity<NeoComSBException>( neocomException, headers, neocomException.getHttpStatus() );
	}

	@ExceptionHandler({ IOException.class })
	public ResponseEntity<NeoComSBException> handleIOException( final IOException ioe ) {
		final NeoComSBException neocomException = new NeoComSBException( ioe );
		// Convert the exception to a json response
		HttpHeaders headers = new HttpHeaders();
		headers.add( "xApp-Error-Message", neocomException.getMessage() );
		headers.add( "xApp-Error-Class", neocomException.getExceptionType() );
		return new ResponseEntity<NeoComSBException>( neocomException, HttpStatus.UNAUTHORIZED );
	}

	@ExceptionHandler({ SQLException.class })
	public ResponseEntity<NeoComSBException> handleSQLException( final SQLException sqle ) {
		final NeoComSBException neocomException = new NeoComSBException( sqle );
		// Convert the exception to a json response
		HttpHeaders headers = new HttpHeaders();
		headers.add( "xApp-Error-Message", neocomException.getMessage() );
		headers.add( "xApp-Error-Class", neocomException.getExceptionType() );
		return new ResponseEntity<NeoComSBException>( neocomException, HttpStatus.UNAUTHORIZED );
	}
}
