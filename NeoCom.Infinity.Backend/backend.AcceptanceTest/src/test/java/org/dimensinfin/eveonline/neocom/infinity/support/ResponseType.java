package org.dimensinfin.eveonline.neocom.infinity.support;

import java.util.Arrays;

import org.apache.commons.lang3.NotImplementedException;

public enum ResponseType {
	VALIDATE_AUTHORIZATION_TOKEN_RESPONSE( "Validate Authorization Token Response" ),
	CORPORATION_DATA_RESPONSE( "Corporation Response" ),
	PILOT_PUBLIC_DATA_RESPONSE( "Pilot Response" );

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
