package org.dimensinfin.eveonline.neocom.infinity.core;

import org.springframework.http.HttpStatus;

public enum ErrorInfo {
	NOT_INTERCEPTED_EXCEPTION("Not intercepted exception.", HttpStatus.BAD_REQUEST),
	AUTHORIZATION_TRANSLATION("TokenTranslationResponse response is not valid.", HttpStatus.BAD_REQUEST),
	VERIFICATION_RESPONSE("VerifyCharacterResponse response is not valid.", HttpStatus.BAD_REQUEST);
	public final String errorMessage;
	public final HttpStatus status;

	ErrorInfo( final String errorMessage, final HttpStatus status ) {
		this.errorMessage = errorMessage;
		this.status = status;
	}
}
