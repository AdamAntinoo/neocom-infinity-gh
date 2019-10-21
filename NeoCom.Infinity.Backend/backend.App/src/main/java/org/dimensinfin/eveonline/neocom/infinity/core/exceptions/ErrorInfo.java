package org.dimensinfin.eveonline.neocom.infinity.core.exceptions;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

public enum ErrorInfo {
	NOT_INTERCEPTED_EXCEPTION( "Not intercepted exception."
			, HttpStatus.BAD_REQUEST ),
	AUTHORIZATION_TRANSLATION( "TokenTranslationResponse response is not valid."
			, HttpStatus.BAD_REQUEST ),
	VERIFICATION_RESPONSE( "VerifyCharacterResponse response is not valid."
			, HttpStatus.BAD_REQUEST ),
	INVALID_CREDENTIAL_IDENTIFIER(
			"The validation character response is not valid and then the unique character identifier is not found."
			, HttpStatus.BAD_REQUEST ),
	CORPORATION_ID_NOT_AUTHORIZED( "The corporation requested is not authorized to the requester."
			, HttpStatus.FORBIDDEN ),
	PILOT_ID_NOT_AUTHORIZED( "The access to the pilot data is not authorized to the requester credential."
			, HttpStatus.FORBIDDEN ),
	TARGET_NOT_FOUND( "The entity of class {0} with identifier {1} is not found.", HttpStatus.NOT_FOUND ),
	CORPORATION_NOT_FOUND( "The entity of class {} with identifier {} is not found.", HttpStatus.NOT_FOUND ),
	PILOT_NOT_FOUND( "The entity of class {0} with identifier {} is not found.", HttpStatus.NOT_FOUND );
	public final String errorMessage;
	public final HttpStatus status;

	ErrorInfo( final String errorMessage, final HttpStatus status ) {
		this.errorMessage = errorMessage;
		this.status = status;
	}

	public String getErrorMessage(final String... arguments) {
		return MessageFormat.format( this.errorMessage, arguments);
	}
}
