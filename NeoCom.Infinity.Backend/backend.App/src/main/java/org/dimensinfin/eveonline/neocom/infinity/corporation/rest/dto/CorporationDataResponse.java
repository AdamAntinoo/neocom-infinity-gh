package org.dimensinfin.eveonline.neocom.infinity.corporation.rest.dto;

public class CorporationDataResponse {
	// - B U I L D E R
	public static class Builder {
		private CorporationDataResponse onConstruction;

		public Builder() {
			this.onConstruction = new CorporationDataResponse();
		}

		public CorporationDataResponse build() {
			return this.onConstruction;
		}
	}
}