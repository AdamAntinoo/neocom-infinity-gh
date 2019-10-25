package org.dimensinfin.eveonline.neocom.infinity.core.exceptions;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

public enum ErrorInfo {
	NOT_INTERCEPTED_EXCEPTION( HttpStatus.BAD_REQUEST,
			"neocom.error.authorization.translation",
			"Not intercepted exception." ),
	AUTHORIZATION_TRANSLATION( HttpStatus.BAD_REQUEST,
			"neocom.error.authorization.translation",
			"TokenTranslationResponse response is not valid."		),
	VERIFICATION_RESPONSE( HttpStatus.BAD_REQUEST,
			"neocom.error.authorization.verification",
			"VerifyCharacterResponse response is not valid."		),
	INVALID_CREDENTIAL_IDENTIFIER(HttpStatus.BAD_REQUEST,
			"neocom.error.authorization.verification",
			"The validation character response is not valid and then the unique character identifier is not found." ),
	CORPORATION_ID_NOT_AUTHORIZED( HttpStatus.FORBIDDEN,
			"neocom.error.authorization.translation",
			"The corporation requested is not authorized to the requester."		),
	PILOT_ID_NOT_AUTHORIZED( HttpStatus.FORBIDDEN,
			"neocom.error.authorization.translation",
			"The access to the pilot data is not authorized to the requester credential."		),
	TARGET_NOT_FOUND( HttpStatus.NOT_FOUND,
			"neocom.error.authorization.translation",
			"The entity of class {0} with identifier {1} is not found."  );

	public final String errorMessage;
	public final HttpStatus status;

	ErrorInfo(final HttpStatus status, final String errorCode,  final String errorMessage ) {
		this.errorMessage = errorMessage;
		this.status = status;
	}

	public String getErrorMessage(final String... arguments) {
		return MessageFormat.format( this.errorMessage, arguments);
	}
}
