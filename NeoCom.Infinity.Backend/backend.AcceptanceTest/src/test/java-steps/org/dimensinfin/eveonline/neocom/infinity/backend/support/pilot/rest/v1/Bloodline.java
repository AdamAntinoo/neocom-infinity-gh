package org.dimensinfin.eveonline.neocom.infinity.backend.support.pilot.rest.v1;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"bloodlineId",
		"charisma",
		"corporationId",
		"description",
		"intelligence",
		"memory",
		"name",
		"perception",
		"raceId",
		"shipTypeId",
		"willpower"
})
public class Bloodline {

	@JsonProperty("bloodlineId")
	private Integer bloodlineId;
	@JsonProperty("charisma")
	private Integer charisma;
	@JsonProperty("corporationId")
	private Integer corporationId;
	@JsonProperty("description")
	private String description;
	@JsonProperty("intelligence")
	private Integer intelligence;
	@JsonProperty("memory")
	private Integer memory;
	@JsonProperty("name")
	private String name;
	@JsonProperty("perception")
	private Integer perception;
	@JsonProperty("raceId")
	private Integer raceId;
	@JsonProperty("shipTypeId")
	private Integer shipTypeId;
	@JsonProperty("willpower")
	private Integer willpower;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("bloodlineId")
	public Integer getBloodlineId() {
		return bloodlineId;
	}

	@JsonProperty("bloodlineId")
	public void setBloodlineId(Integer bloodlineId) {
		this.bloodlineId = bloodlineId;
	}

	@JsonProperty("charisma")
	public Integer getCharisma() {
		return charisma;
	}

	@JsonProperty("charisma")
	public void setCharisma(Integer charisma) {
		this.charisma = charisma;
	}

	@JsonProperty("corporationId")
	public Integer getCorporationId() {
		return corporationId;
	}

	@JsonProperty("corporationId")
	public void setCorporationId(Integer corporationId) {
		this.corporationId = corporationId;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("intelligence")
	public Integer getIntelligence() {
		return intelligence;
	}

	@JsonProperty("intelligence")
	public void setIntelligence(Integer intelligence) {
		this.intelligence = intelligence;
	}

	@JsonProperty("memory")
	public Integer getMemory() {
		return memory;
	}

	@JsonProperty("memory")
	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("perception")
	public Integer getPerception() {
		return perception;
	}

	@JsonProperty("perception")
	public void setPerception(Integer perception) {
		this.perception = perception;
	}

	@JsonProperty("raceId")
	public Integer getRaceId() {
		return raceId;
	}

	@JsonProperty("raceId")
	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

	@JsonProperty("shipTypeId")
	public Integer getShipTypeId() {
		return shipTypeId;
	}

	@JsonProperty("shipTypeId")
	public void setShipTypeId(Integer shipTypeId) {
		this.shipTypeId = shipTypeId;
	}

	@JsonProperty("willpower")
	public Integer getWillpower() {
		return willpower;
	}

	@JsonProperty("willpower")
	public void setWillpower(Integer willpower) {
		this.willpower = willpower;
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
