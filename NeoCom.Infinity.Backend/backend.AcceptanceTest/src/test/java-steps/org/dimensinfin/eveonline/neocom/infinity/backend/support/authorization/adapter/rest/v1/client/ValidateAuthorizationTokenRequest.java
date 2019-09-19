package org.dimensinfin.eveonline.neocom.infinity.backend.support.authorization.adapter.rest.v1.client;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class ValidateAuthorizationTokenRequest {
	private static final String DEFAULT_ESI_SERVER = "tranquility";
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

	public String getDataSourceName() {
		if (this.dataSource.isPresent())
			return this.dataSource.get();
		else return DEFAULT_ESI_SERVER;
	}

	// - B U I L D E R
	public static class Builder {
		private ValidateAuthorizationTokenRequest onConstruction;

		public Builder() {
			this.onConstruction = new ValidateAuthorizationTokenRequest();
		}

		public ValidateAuthorizationTokenRequest.Builder withCode( final String code ) {
			Objects.requireNonNull( code );
			this.onConstruction.code = code;
			return this;
		}

		public ValidateAuthorizationTokenRequest.Builder withState( final String state ) {
			Objects.requireNonNull( state );
			this.onConstruction.state = state;
			return this;
		}

		public ValidateAuthorizationTokenRequest.Builder optionalDataSource( final String dataSource ) {
			if (null != dataSource)
				if (!dataSource.isEmpty())
					this.onConstruction.dataSource = Optional.of( dataSource );
			return this;
		}

		public ValidateAuthorizationTokenRequest build() {
			Objects.requireNonNull( this.onConstruction.code );
			Objects.requireNonNull( this.onConstruction.state );
			return this.onConstruction;
		}
	}
}
