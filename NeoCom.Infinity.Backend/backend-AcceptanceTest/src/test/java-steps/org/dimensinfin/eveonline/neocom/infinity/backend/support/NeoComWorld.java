package org.dimensinfin.eveonline.neocom.infinity.backend.support;

import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;

public class NeoComWorld {
	private ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest;

	public ValidateAuthorizationTokenRequest getValidateAuthorizationTokenRequest() {
		return validateAuthorizationTokenRequest;
	}

	public NeoComWorld setValidateAuthorizationTokenRequest( final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest ) {
		this.validateAuthorizationTokenRequest = validateAuthorizationTokenRequest;
		return this;
	}
}
