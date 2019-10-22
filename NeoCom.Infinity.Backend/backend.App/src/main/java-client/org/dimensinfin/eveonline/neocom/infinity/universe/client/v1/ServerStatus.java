package org.dimensinfin.eveonline.neocom.infinity.universe.client.v1;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.dimensinfin.eveonline.neocom.esiswagger.model.GetStatusOk;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ServerStatus {
	private String server = "Tranquility";
	private GetStatusOk status;

	public String getServer() {
		return server;
	}

	public GetStatusOk getStatus() {
		return status;
	}

	public ServerStatus setServer( final String server ) {
		this.server = server;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private ServerStatus onConstruction;

		public Builder() {
			this.onConstruction = new ServerStatus();
		}

		public ServerStatus.Builder withServer( final String server ) {
			Objects.requireNonNull( server );
			this.onConstruction.server = server;
			return this;
		}

		public ServerStatus.Builder withStatus( final GetStatusOk status ) {
			Objects.requireNonNull( status );
			this.onConstruction.status = status;
			return this;
		}

		public ServerStatus build() {
			Objects.requireNonNull( this.onConstruction.server );
			Objects.requireNonNull( this.onConstruction.status );
			return this.onConstruction;
		}
	}
}