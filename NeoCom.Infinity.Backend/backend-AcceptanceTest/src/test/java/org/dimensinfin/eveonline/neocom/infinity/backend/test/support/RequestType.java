package org.dimensinfin.eveonline.neocom.infinity.backend.test.support;

import org.apache.commons.lang.NotImplementedException;

import java.util.Arrays;

public enum RequestType {
	VALIDATE_AUTHORIZATION_TOKEN_ENDPOINT_NAME("Validate Authorization Token");

	public static RequestType from( String code ) {
		return Arrays.stream(RequestType.values())
				       .filter(requestType -> requestType.code.equals(code))
				       .findFirst()
				       .orElseThrow(NotImplementedException::new);
	}
	private String code;

	RequestType( String code ) {
		this.code = code;
	}
}
