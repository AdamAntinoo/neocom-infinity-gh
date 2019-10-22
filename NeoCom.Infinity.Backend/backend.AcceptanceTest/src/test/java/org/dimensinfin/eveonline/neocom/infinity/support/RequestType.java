package org.dimensinfin.eveonline.neocom.infinity.support;

import java.util.Arrays;

import org.apache.commons.lang3.NotImplementedException;

public enum RequestType {
	VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME( "Validate Authorization Token" ),
	GET_SERVER( " Get Server Status"),
	GET_CORPORATION_ENDPOINT_NAME( "Get Corporation Data" ),
	GET_PILOT_ENDPOINT_NAME( "Get Pilot Data" );

	public static RequestType from( final String code ) {
		return Arrays.stream( RequestType.values() )
				.filter( requestType -> requestType.code.equals( code ) )
				.findFirst()
				.orElseThrow( () -> new NotImplementedException( "Request type not implemented." ) );
	}

	private String code;

	RequestType( String code ) {
		this.code = code;
	}
}
