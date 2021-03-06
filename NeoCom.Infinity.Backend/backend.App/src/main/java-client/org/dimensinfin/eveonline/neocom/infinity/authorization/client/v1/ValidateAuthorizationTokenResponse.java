package org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.infinity.core.client.v0.NeoComResponse;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidateAuthorizationTokenResponse extends NeoComResponse {
	private String jwtToken;
	private Credential credential;

	private ValidateAuthorizationTokenResponse() {}

	public Credential getCredential() {
		return this.credential;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	// - B U I L D E R
	public static class Builder {
		private ValidateAuthorizationTokenResponse onConstruction;

		public Builder() {
			this.onConstruction = new ValidateAuthorizationTokenResponse();
		}

		public ValidateAuthorizationTokenResponse.Builder withCredential( final Credential credential ) {
			Objects.requireNonNull(credential);
			this.onConstruction.credential = credential;
			return this;
		}
		public ValidateAuthorizationTokenResponse.Builder withJwtToken( final String jwtToken ) {
			Objects.requireNonNull(jwtToken);
			this.onConstruction.jwtToken = jwtToken;
			return this;
		}

		public ValidateAuthorizationTokenResponse build() {
			return this.onConstruction;
		}
	}
}
