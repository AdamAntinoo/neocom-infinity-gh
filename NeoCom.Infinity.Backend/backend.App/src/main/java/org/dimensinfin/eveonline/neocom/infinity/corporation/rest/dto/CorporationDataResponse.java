package org.dimensinfin.eveonline.neocom.infinity.corporation.rest.dto;

import org.dimensinfin.eveonline.neocom.domain.EsiLocation;

public class CorporationDataResponse {
	private int ceoId;
	private String dateFunded;
	private String description;
	private EsiLocation homeStation;
	private int memberCount;
	private String name;
	private String ticker;
	private String urlSite;

	public int getCeoId() {
		return ceoId;
	}

	public CorporationDataResponse setCeoId( final int ceoId ) {
		this.ceoId = ceoId;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private CorporationDataResponse onConstruction;

		public Builder() {
			this.onConstruction = new CorporationDataResponse();
		}

		public CorporationDataResponse.Builder withCeoId( final int ceoId ) {
			this.onConstruction.ceoId = ceoId;
			return this;
		}

		public CorporationDataResponse build() {
			return this.onConstruction;
		}
	}
}