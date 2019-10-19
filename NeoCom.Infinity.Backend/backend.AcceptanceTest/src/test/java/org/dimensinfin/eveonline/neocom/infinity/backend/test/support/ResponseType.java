package org.dimensinfin.eveonline.neocom.infinity.backend.test.support;

import java.util.Arrays;

import org.apache.commons.lang3.NotImplementedException;

public enum ResponseType {
	VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME( "Validate Authorization Token Response" );

	public static ResponseType from( final String code ) {
		return Arrays.stream( ResponseType.values() )
				.filter( responseType -> responseType.code.equals( code ) )
				.findFirst()
				.orElseThrow( () -> new NotImplementedException( "Response type not implemented." ) );
	}

	private String code;

	ResponseType( String code ) {
		this.code = code;
	}
}
