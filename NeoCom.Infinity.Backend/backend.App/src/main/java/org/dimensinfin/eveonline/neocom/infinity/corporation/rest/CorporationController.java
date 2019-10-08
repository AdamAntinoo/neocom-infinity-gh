package org.dimensinfin.eveonline.neocom.infinity.corporation.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.dimensinfin.eveonline.neocom.infinity.core.NeoComController;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComSBException;
import org.dimensinfin.eveonline.neocom.infinity.corporation.CorporationService;
import org.dimensinfin.eveonline.neocom.infinity.corporation.rest.dto.CorporationDataResponse;

@RestController
@Validated
@RequestMapping("/api/v1/neocom")
public class CorporationController extends NeoComController {
	private final CorporationService corporationService;

	@Autowired
	public CorporationController( final CorporationService corporationService ) {
		this.corporationService = corporationService;
	}

	@GetMapping(path="/corporation/{corporationId}",
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<CorporationDataResponse> getCorporationData( @PathVariable final Integer corporationId ) {
			logger.info( ">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/neocom/corporation/{}",
					corporationId );
		return  this.corporationService.getCorporationData( corporationId);
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
