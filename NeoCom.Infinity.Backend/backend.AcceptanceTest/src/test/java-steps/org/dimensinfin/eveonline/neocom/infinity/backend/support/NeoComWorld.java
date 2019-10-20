package org.dimensinfin.eveonline.neocom.infinity.backend.support;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import org.dimensinfin.eveonline.neocom.domain.Pilot;
import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.corporation.client.v1.CorporationDataResponse;

public class NeoComWorld {
	private int httpStatusCodeValue;

	private ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest;
	private ValidateAuthorizationTokenResponse validateAuthorizationTokenResponse;
	private Optional<Integer> corporationIdentifier = Optional.empty();
	private Optional<Integer> pilotIdentifier = Optional.empty();
	private String jwtAuthorizationToken;
	private ResponseEntity<CorporationDataResponse> corporationDataResponse;
	private ResponseEntity<Pilot> pilotResponse;
	private Pilot pilot;

	public int getHttpStatusCodeValue() {
		return httpStatusCodeValue;
	}

	public NeoComWorld setHttpStatusCodeValue( final int httpStatusCodeValue ) {
		this.httpStatusCodeValue = httpStatusCodeValue;
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
		return this.validateAuthorizationTokenResponse;
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

	public Optional<Integer> getPilotIdentifier() {
		return pilotIdentifier;
	}

	public NeoComWorld setPilotIdentifier( final Integer pilotIdentifier ) {
		if (null != pilotIdentifier) this.pilotIdentifier = Optional.of( pilotIdentifier );
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
		return this.corporationDataResponse;
	}

	public NeoComWorld setCorporationDataResponse( final ResponseEntity<CorporationDataResponse> corporationDataResponse ) {
		this.corporationDataResponse = corporationDataResponse;
		this.httpStatusCodeValue = corporationDataResponse.getStatusCodeValue();
		return this;
	}

	public ResponseEntity<Pilot> getPilotResponse() {
		return pilotResponse;
	}

	public NeoComWorld setPilotResponse( final ResponseEntity<Pilot> pilotResponse ) {
		this.pilotResponse = pilotResponse;
		return this;
	}

	public Pilot getPilot() {
		return pilot;
	}

	public NeoComWorld setPilot( final Pilot pilot ) {
		this.pilot = pilot;
		return this;
	}
}
