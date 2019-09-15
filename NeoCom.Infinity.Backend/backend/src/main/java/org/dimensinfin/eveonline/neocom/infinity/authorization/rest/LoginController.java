package org.dimensinfin.eveonline.neocom.infinity.authorization.rest;

import org.dimensinfin.eveonline.neocom.exception.NeoComException;
import org.dimensinfin.eveonline.neocom.infinity.authorization.AuthorizationService;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComController;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComSBException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/v1/neocom")
public class LoginController extends NeoComController {
	private final AuthorizationService authorizationService;

	@Autowired
	public LoginController( final AuthorizationService authorizationService ) {
		this.authorizationService = authorizationService;
	}

	//	@CrossOrigin()
	@GetMapping(path = {
			"/validateAuthorizationToken/{code}/state/{state}",
			"/validateAuthorizationToken/{code}/state/{state}/datasource/{dataSource}" },
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationToken( @PathVariable final String code,
	                                                                                      @PathVariable final String state,
	                                                                                      @PathVariable("dataSource") final Optional<String> dataSource
	) throws NeoComException, IOException, SQLException {
		if (dataSource.isPresent())
			logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/validateAuthorizationToken/{}/state/{}/datasource/{}",
			            code, state, dataSource);
		else
			logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/validateAuthorizationToken/{}/state/{}", code, state);
		final ValidateAuthorizationTokenRequest.Builder requestBuilder = new ValidateAuthorizationTokenRequest.Builder()
				                                                                 .withCode(code)
				                                                                 .withState(state);
		if (dataSource.isPresent()) requestBuilder.optionalDataSource(dataSource.get());
		return new ResponseEntity(this.authorizationService.validateAuthorizationToken(requestBuilder.build()),
		                          HttpStatus.OK);
	}


//	private String exceptionSerialization(final Exception exc) {
//		String serializedException = null;
//		try {
//			serializedException = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(exc);
//		} catch (JsonProcessingException jpe) {
//			jpe.printStackTrace();
//			serializedException = new JsonExceptionInstance(jpe.getMessage()).toJson();
//		}
//		return serializedException;
//	}
//
//	private String serializeCredentialList(final List<Credential> credentials) {
//		// Use my own serialization control to return the data to generate exactly what I want.
//		String contentsSerialized = "[jsonClass: \"Exception\"," +
//				"message: \"Unprocessed data. Possible JsonProcessingException exception.\"]";
//		try {
//			contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(credentials);
//		} catch (JsonProcessingException jpe) {
//			jpe.printStackTrace();
//			contentsSerialized = new JsonExceptionInstance(jpe.getMessage()).toJson();
//		}
//		return contentsSerialized;
//	}

//
//	public static class LoginResponse {
//		private String authorizationToken;
//		private PilotV2 pilotPublicData;
//
//		public String getAuthorizationToken() {
//			return authorizationToken;
//		}
//
//		public LoginResponse setAuthorizationToken(final String authorizationToken) {
//			this.authorizationToken = authorizationToken;
//			return this;
//		}
//
//		public PilotV2 getPilotPublicData() {
//			return pilotPublicData;
//		}
//
//		public LoginResponse setPilotPublicData(final PilotV2 pilotPublicData) {
//			this.pilotPublicData = pilotPublicData;
//			return this;
//		}
//	}

	@ExceptionHandler(NeoComSBException.class)
	public ResponseEntity<NeoComSBException> handleNeoComSBException( final NeoComSBException neocomException ) {
		// Convert the exception to a json response
		HttpHeaders headers = new HttpHeaders();
		headers.add("xApp-Error-Message", neocomException.getMessage());
		logger.info("EX [LoginController.handleNeoComSBException]> Exception: {}", neocomException.getMessage());
		return new ResponseEntity<NeoComSBException>(neocomException, headers, neocomException.getHttpStatus());
	}

	@ExceptionHandler({ IOException.class })
	public ResponseEntity<NeoComSBException> handleIOException( final IOException ioe ) {
		return new ResponseEntity<NeoComSBException>(new NeoComSBException(ioe), HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler({ SQLException.class })
	public ResponseEntity<NeoComSBException> handleSQLException( final SQLException sqle ) {
		return new ResponseEntity<NeoComSBException>(new NeoComSBException(sqle), HttpStatus.UNAUTHORIZED);
	}
}
