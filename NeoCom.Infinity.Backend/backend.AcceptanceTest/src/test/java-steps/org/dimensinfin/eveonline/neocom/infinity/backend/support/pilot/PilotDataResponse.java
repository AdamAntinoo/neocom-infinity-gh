package org.dimensinfin.eveonline.neocom.infinity.backend.support.pilot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PilotDataResponse {
	private int id;
	private int corporationId;
	private String gender;
	private String name;

	// - B U I L D E R
	public static class Builder {
		private PilotDataResponse onConstruction;

		public Builder() {
			this.onConstruction = new PilotDataResponse();
		}

		public PilotDataResponse build() {
			return this.onConstruction;
		}
	}
}