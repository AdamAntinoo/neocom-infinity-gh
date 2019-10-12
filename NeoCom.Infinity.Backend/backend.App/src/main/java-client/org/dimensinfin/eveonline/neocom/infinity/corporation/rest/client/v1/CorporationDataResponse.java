package org.dimensinfin.eveonline.neocom.infinity.corporation.rest.client.v1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.dimensinfin.eveonline.neocom.domain.EsiLocation;
import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdOk;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CorporationDataResponse {
	private int ceoId;
	private GetCharactersCharacterIdOk ceoPilotData;
	private String dateFunded;
	private String description;
	private EsiLocation homeStation;
	private int memberCount;
	private String name;
	private String ticker;
	private String urlSite;

	public int getCeoId() {
		return this.ceoId;
	}

	public GetCharactersCharacterIdOk getCeoPilotData() {
		return this.ceoPilotData;
	}

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public int getMemberCount() {
		return this.memberCount;
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

		public CorporationDataResponse.Builder withCeoPilotData( final GetCharactersCharacterIdOk ceoPilotData ) {
			this.onConstruction.ceoPilotData = ceoPilotData;
			return this;
		}

		public CorporationDataResponse.Builder withDescription( final String description ) {
			this.onConstruction.description = description;
			return this;
		}

		public CorporationDataResponse.Builder withMemberCount( final int memberCount ) {
			this.onConstruction.memberCount = memberCount;
			return this;
		}

		public CorporationDataResponse.Builder withName( final String name ) {
			this.onConstruction.name = name;
			return this;
		}

		public CorporationDataResponse build() {
			return this.onConstruction;
		}
	}
}