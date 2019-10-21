package org.dimensinfin.eveonline.neocom.infinity.support.pilot.rest.v1;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Race {

	@JsonProperty("allianceId")
	private Integer allianceId;
	@JsonProperty("description")
	private String description;
	@JsonProperty("name")
	private String name;
	@JsonProperty("raceId")
	private Integer raceId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("allianceId")
	public Integer getAllianceId() {
		return allianceId;
	}

	@JsonProperty("allianceId")
	public void setAllianceId(Integer allianceId) {
		this.allianceId = allianceId;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("raceId")
	public Integer getRaceId() {
		return raceId;
	}

	@JsonProperty("raceId")
	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}