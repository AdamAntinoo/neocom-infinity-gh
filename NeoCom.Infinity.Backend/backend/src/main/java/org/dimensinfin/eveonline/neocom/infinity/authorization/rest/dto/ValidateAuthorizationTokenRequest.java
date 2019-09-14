package org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto;

import com.annimon.stream.Optional;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ValidateAuthorizationTokenRequest {
	private String code;
	private String state;
	private Optional<String> dataSource = Optional.empty();

	private ValidateAuthorizationTokenRequest() {}

	public String getCode() {
		return this.code;
	}

	public String getState() {
		return this.state;
	}

	public Optional<String> getDataSource() {
		return this.dataSource;
	}

	// - B U I L D E R
	public static class Builder {
		private ValidateAuthorizationTokenRequest onConstruction;

		public Builder() {
			this.onConstruction = new ValidateAuthorizationTokenRequest();
		}

		public ValidateAuthorizationTokenRequest.Builder withCode( final String code ) {
			Objects.requireNonNull(code);
			this.onConstruction.code = code;
			return this;
		}

		public ValidateAuthorizationTokenRequest.Builder withState( final String state ) {
			Objects.requireNonNull(state);
			this.onConstruction.state = state;
			return this;
		}

		public ValidateAuthorizationTokenRequest.Builder optionalDataSource( final String dataSource ) {
			if (null != dataSource)
				this.onConstruction.dataSource = Optional.of(dataSource);
			return this;
		}

		public ValidateAuthorizationTokenRequest build() {
			Objects.requireNonNull(this.onConstruction.code);
			Objects.requireNonNull(this.onConstruction.state);
			return this.onConstruction;
		}
	}
}
