package org.dimensinfin.eveonline.neocom.infinity.pilot.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.dimensinfin.eveonline.neocom.esiswagger.model.GetCharactersCharacterIdOk;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PilotDataResponse {
	private int id;
	private int corporationId;
	private GetCharactersCharacterIdOk.GenderEnum gender;
	private String name;

	public int getId() {
		return id;
	}

	public int getCorporationId() {
		return corporationId;
	}

	public GetCharactersCharacterIdOk.GenderEnum getGender() {
		return gender;
	}

	public String getName() {
		return name;
	}

	public PilotDataResponse setPilotId( final int pilotId ) {
		this.id = pilotId;
		return this;
	}

	// - B U I L D E R
	public static class Builder {
		private PilotDataResponse onConstruction;

		public Builder() {
			this.onConstruction = new PilotDataResponse();
		}

		public PilotDataResponse.Builder withCorporationId( final int corporationId ) {
			this.onConstruction.corporationId = corporationId;
			return this;
		}

		public PilotDataResponse.Builder withGender( final GetCharactersCharacterIdOk.GenderEnum gender ) {
			this.onConstruction.gender = gender;
			return this;
		}

		public PilotDataResponse.Builder withName( final String name ) {
			this.onConstruction.name = name;
			return this;
		}

		public PilotDataResponse build() {
			return this.onConstruction;
		}
	}
}