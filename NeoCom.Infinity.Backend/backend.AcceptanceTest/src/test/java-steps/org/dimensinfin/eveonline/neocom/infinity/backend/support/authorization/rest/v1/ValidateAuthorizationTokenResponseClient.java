package org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.rest.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateAuthorizationTokenResponseClient {
	public Object headers;
	public ValidateAuthorizationTokenResponse body;
	public String statusCode;
	public Integer statusCodeValue;

	public Object getHeaders() {
		return headers;
	}

	public ValidateAuthorizationTokenResponseClient setHeaders( final Object headers ) {
		this.headers = headers;
		return this;
	}

	public ValidateAuthorizationTokenResponse getBody() {
		return body;
	}

	public ValidateAuthorizationTokenResponseClient setBody( final ValidateAuthorizationTokenResponse body ) {
		this.body = body;
		return this;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public ValidateAuthorizationTokenResponseClient setStatusCode( final String statusCode ) {
		this.statusCode = statusCode;
		return this;
	}

	public Integer getStatusCodeValue() {
		return statusCodeValue;
	}

	public ValidateAuthorizationTokenResponseClient setStatusCodeValue( final Integer statusCodeValue ) {
		this.statusCodeValue = statusCodeValue;
		return this;
	}
}