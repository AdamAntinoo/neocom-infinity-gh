package org.dimensinfin.eveonline.neocom.infinity.backend.support;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.backend.support.corporation.rest.v1.CorporationDataResponse;

public class NeoComWorld {
	private ResponseEntity responseEntity;
	private int httpStatusCodeValue;

	private ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest;
	private ValidateAuthorizationTokenResponse validateAuthorizationTokenResponse;
	private Optional<Integer> corporationIdentifier = Optional.empty();
	private String jwtAuthorizationToken;
	private ResponseEntity<CorporationDataResponse> corporationDataResponse;

	public int getHttpStatusCodeValue() {
		return httpStatusCodeValue;
	}

	public NeoComWorld setHttpStatusCodeValue( final int httpStatusCodeValue ) {
		this.httpStatusCodeValue = httpStatusCodeValue;
		return this;
	}

	@Deprecated
	public ResponseEntity getResponseEntity() {
		return responseEntity;
	}

	@Deprecated
	public NeoComWorld setResponseEntity( final ResponseEntity responseEntity ) {
		this.responseEntity = responseEntity;
		return this;
	}

	public ValidateAuthorizationTokenRequest getValidateAuthorizationTokenRequest() {
		return validateAuthorizationTokenRequest;
	}

	public NeoComWorld setValidateAuthorizationTokenRequest( final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest ) {
		this.validateAuthorizationTokenRequest = validateAuthorizationTokenRequest;
		return this;
	}

	public ValidateAuthorizationTokenResponse getValidateAuthorizationTokenResponse() {
		return validateAuthorizationTokenResponse;
	}

	public NeoComWorld setValidateAuthorizationTokenResponse( final ValidateAuthorizationTokenResponse validateAuthorizationTokenResponse ) {
		this.validateAuthorizationTokenResponse = validateAuthorizationTokenResponse;
		return this;
	}

	public Optional<Integer> getCorporationIdentifier() {
		return corporationIdentifier;
	}

	public NeoComWorld setCorporationIdentifier( final Integer corporationIdentifier ) {
		if (null != corporationIdentifier) this.corporationIdentifier = Optional.of( corporationIdentifier );
		return this;
	}

	public String getJwtAuthorizationToken() {
		return jwtAuthorizationToken;
	}

	public NeoComWorld setJwtAuthorizationToken( final String jwtAuthorizationToken ) {
		this.jwtAuthorizationToken = jwtAuthorizationToken;
		return this;
	}

	public ResponseEntity<CorporationDataResponse> getCorporationDataResponse() {
		return corporationDataResponse;
	}

	public NeoComWorld setCorporationDataResponse( final ResponseEntity<CorporationDataResponse> corporationDataResponse ) {
		this.corporationDataResponse = corporationDataResponse;
		this.httpStatusCodeValue =corporationDataResponse.getStatusCodeValue();
		return this;
	}
}
