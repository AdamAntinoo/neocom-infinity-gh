package org.dimensinfin.eveonline.neocom.infinity.core;

import java.text.MessageFormat;

public enum ExceptionMessagesExternalisedType {
	CREDENTIAL_NOT_FOUND( "Credential not authenticated. Credential not found on repository." ),
	CREDENTIAL_NOT_FOUND_BECAUSE_EXCEPTION( "Credential not authenticated. Not found because SQL exception." );

	private String message;

	ExceptionMessagesExternalisedType( final String message ) {
		this.message = message;
	}

	public String getMessage( Object... arguments ) {
		return MessageFormat.format( this.message, arguments );
	}
}