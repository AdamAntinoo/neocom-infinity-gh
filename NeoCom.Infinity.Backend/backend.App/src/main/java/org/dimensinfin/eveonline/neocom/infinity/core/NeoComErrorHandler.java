package org.dimensinfin.eveonline.neocom.infinity.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.NeoComAuthorizationException;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.NeoComSBException;

@RestControllerAdvice
public class NeoComErrorHandler {
	protected static Logger logger = LoggerFactory.getLogger( NeoComErrorHandler.class );

	@ExceptionHandler(NeoComAuthorizationException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public NeoComAuthorizationException handleNeoComAuthorizationException( final NeoComAuthorizationException neocomException ) {
		return neocomException;
	}
	@ExceptionHandler(NeoComSBException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public NeoComSBException handleNeoComSBException( final NeoComSBException neocomException ) {
		return neocomException;
	}
}