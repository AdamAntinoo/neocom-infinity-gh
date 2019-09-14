package org.dimensinfin.eveonline.neocom.infinity.backend.support;

import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.springframework.http.HttpStatus;

public class NeoComWorld {
	private ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest;
	private HttpStatus httpStatus;
	private int httpStatusCode;
	private ValidateAuthorizationTokenResponse validateAuthorizationTokenResponse;

	public ValidateAuthorizationTokenRequest getValidateAuthorizationTokenRequest() {
		return validateAuthorizationTokenRequest;
	}

	public NeoComWorld setValidateAuthorizationTokenRequest( final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest ) {
		this.validateAuthorizationTokenRequest = validateAuthorizationTokenRequest;
		return this;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public NeoComWorld setHttpStatusCode( final int httpStatusCode ) {
		this.httpStatusCode = httpStatusCode;
		return this;
	}

	public ValidateAuthorizationTokenResponse getValidateAuthorizationTokenResponse() {
		return validateAuthorizationTokenResponse;
	}

	public NeoComWorld setValidateAuthorizationTokenResponse( final ValidateAuthorizationTokenResponse validateAuthorizationTokenResponse ) {
		this.validateAuthorizationTokenResponse = validateAuthorizationTokenResponse;
		return this;
	}
}
