package org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidateAuthorizationTokenResponse {
	private Credential credential;

	private ValidateAuthorizationTokenResponse() {}

	public Credential getCredential() {
		return this.credential;
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

		public ValidateAuthorizationTokenResponse build() {
			return this.onConstruction;
		}
	}
}
