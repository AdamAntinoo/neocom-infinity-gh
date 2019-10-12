package org.dimensinfin.eveonline.neocom.infinity.corporation;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.infinity.core.ErrorInfo;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComController;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComSBException;
import org.dimensinfin.eveonline.neocom.infinity.corporation.rest.dto.CorporationDataResponse;
import org.dimensinfin.eveonline.neocom.infinity.security.NeoComAuthenticationProvider;

@RestController
@Validated
@RequestMapping("/api/v1/neocom")
public class CorporationController extends NeoComController {
	private final CorporationService corporationService;
	private final NeoComAuthenticationProvider neoComAuthenticationProvider;

	@Autowired
	public CorporationController( final CorporationService corporationService,
	                              final NeoComAuthenticationProvider neoComAuthenticationProvider ) {
		this.corporationService = corporationService;
		this.neoComAuthenticationProvider = neoComAuthenticationProvider;
	}

	@GetMapping(path = "/corporation/{corporationId}",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CorporationDataResponse> getCorporationData( @PathVariable final Integer corporationId ) {
		logger.info( ">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/neocom/corporation/{}",
				corporationId );
		// Check corporation identifier access is authorized.
		try {
			final Integer authorizedCorporationId = this.neoComAuthenticationProvider.getAuthenticatedCorporation();
			if (authorizedCorporationId.intValue() != corporationId.intValue())
				throw new NeoComSBException( ErrorInfo.CORPORATION_ID_NOT_AUTHORIZED );
		} catch (final IOException ioe) {
			throw new NeoComSBException( ErrorInfo.CORPORATION_ID_NOT_AUTHORIZED );
		}
		return this.corporationService.getCorporationData( corporationId );
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

//	@ExceptionHandler({ IOException.class })
//	public ResponseEntity<NeoComSBException> handleIOException( final IOException ioe ) {
//		final NeoComSBException neocomException = new NeoComSBException( ioe );
//		// Convert the exception to a json response
//		HttpHeaders headers = new HttpHeaders();
//		headers.add( "xApp-Error-Message", neocomException.getMessage() );
//		headers.add( "xApp-Error-Class", neocomException.getExceptionType() );
//		return new ResponseEntity<NeoComSBException>( neocomException, HttpStatus.UNAUTHORIZED );
//	}
//
//	@ExceptionHandler({ SQLException.class })
//	public ResponseEntity<NeoComSBException> handleSQLException( final SQLException sqle ) {
//		final NeoComSBException neocomException = new NeoComSBException( sqle );
//		// Convert the exception to a json response
//		HttpHeaders headers = new HttpHeaders();
//		headers.add( "xApp-Error-Message", neocomException.getMessage() );
//		headers.add( "xApp-Error-Class", neocomException.getExceptionType() );
//		return new ResponseEntity<NeoComSBException>( neocomException, HttpStatus.UNAUTHORIZED );
//	}
}
