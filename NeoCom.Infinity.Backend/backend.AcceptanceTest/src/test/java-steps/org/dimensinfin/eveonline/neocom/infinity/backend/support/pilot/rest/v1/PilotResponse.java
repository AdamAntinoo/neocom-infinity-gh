package org.dimensinfin.eveonline.neocom.infinity.backend.support.pilot.rest.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
		"lastUpdateTime",
		"pilotId",
		"lastKnownLocation",
		"locationRoles",
		"actions4Pilot",
		"credential",
		"race",
		"ancestry",
		"bloodline",
		"url4Icon",
		"raceId",
		"bloodlineId",
		"ancestryId",
		"corporationId",
		"gender",
		"securityStatus",
		"birthday",
		"name",
		"description",
		"jsonClass"
})
public class PilotResponse {

	@JsonProperty("lastUpdateTime")
	private String lastUpdateTime;
	@JsonProperty("pilotId")
	private Integer pilotId;
	@JsonProperty("lastKnownLocation")
	private Object lastKnownLocation;
	@JsonProperty("locationRoles")
	private List<Object> locationRoles = null;
	@JsonProperty("actions4Pilot")
	private Actions4Pilot actions4Pilot;
	@JsonProperty("credential")
	private Object credential;
	@JsonProperty("race")
	private Race race;
	@JsonProperty("ancestry")
	private Ancestry ancestry;
	@JsonProperty("bloodline")
	private Bloodline bloodline;
	@JsonProperty("url4Icon")
	private String url4Icon;
	@JsonProperty("raceId")
	private Integer raceId;
	@JsonProperty("bloodlineId")
	private Integer bloodlineId;
	@JsonProperty("ancestryId")
	private Integer ancestryId;
	@JsonProperty("corporationId")
	private Integer corporationId;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("securityStatus")
	private Integer securityStatus;
	@JsonProperty("birthday")
	private String birthday;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("jsonClass")
	private String jsonClass;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("lastUpdateTime")
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	@JsonProperty("lastUpdateTime")
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@JsonProperty("pilotId")
	public Integer getPilotId() {
		return pilotId;
	}

	@JsonProperty("pilotId")
	public void setPilotId(Integer pilotId) {
		this.pilotId = pilotId;
	}

	@JsonProperty("lastKnownLocation")
	public Object getLastKnownLocation() {
		return lastKnownLocation;
	}

	@JsonProperty("lastKnownLocation")
	public void setLastKnownLocation(Object lastKnownLocation) {
		this.lastKnownLocation = lastKnownLocation;
	}

	@JsonProperty("locationRoles")
	public List<Object> getLocationRoles() {
		return locationRoles;
	}

	@JsonProperty("locationRoles")
	public void setLocationRoles(List<Object> locationRoles) {
		this.locationRoles = locationRoles;
	}

	@JsonProperty("actions4Pilot")
	public Actions4Pilot getActions4Pilot() {
		return actions4Pilot;
	}

	@JsonProperty("actions4Pilot")
	public void setActions4Pilot(Actions4Pilot actions4Pilot) {
		this.actions4Pilot = actions4Pilot;
	}

	@JsonProperty("credential")
	public Object getCredential() {
		return credential;
	}

	@JsonProperty("credential")
	public void setCredential(Object credential) {
		this.credential = credential;
	}

	@JsonProperty("race")
	public Race getRace() {
		return race;
	}

	@JsonProperty("race")
	public void setRace(Race race) {
		this.race = race;
	}

	@JsonProperty("ancestry")
	public Ancestry getAncestry() {
		return ancestry;
	}

	@JsonProperty("ancestry")
	public void setAncestry(Ancestry ancestry) {
		this.ancestry = ancestry;
	}

	@JsonProperty("bloodline")
	public Bloodline getBloodline() {
		return bloodline;
	}

	@JsonProperty("bloodline")
	public void setBloodline(Bloodline bloodline) {
		this.bloodline = bloodline;
	}

	@JsonProperty("url4Icon")
	public String getUrl4Icon() {
		return url4Icon;
	}

	@JsonProperty("url4Icon")
	public void setUrl4Icon(String url4Icon) {
		this.url4Icon = url4Icon;
	}

	@JsonProperty("raceId")
	public Integer getRaceId() {
		return raceId;
	}

	@JsonProperty("raceId")
	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}

	@JsonProperty("bloodlineId")
	public Integer getBloodlineId() {
		return bloodlineId;
	}

	@JsonProperty("bloodlineId")
	public void setBloodlineId(Integer bloodlineId) {
		this.bloodlineId = bloodlineId;
	}

	@JsonProperty("ancestryId")
	public Integer getAncestryId() {
		return ancestryId;
	}

	@JsonProperty("ancestryId")
	public void setAncestryId(Integer ancestryId) {
		this.ancestryId = ancestryId;
	}

	@JsonProperty("corporationId")
	public Integer getCorporationId() {
		return corporationId;
	}

	@JsonProperty("corporationId")
	public void setCorporationId(Integer corporationId) {
		this.corporationId = corporationId;
	}

	@JsonProperty("gender")
	public String getGender() {
		return gender;
	}

	@JsonProperty("gender")
	public void setGender(String gender) {
		this.gender = gender;
	}

	@JsonProperty("securityStatus")
	public Integer getSecurityStatus() {
		return securityStatus;
	}

	@JsonProperty("securityStatus")
	public void setSecurityStatus(Integer securityStatus) {
		this.securityStatus = securityStatus;
	}

	@JsonProperty("birthday")
	public String getBirthday() {
		return birthday;
	}

	@JsonProperty("birthday")
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("jsonClass")
	public String getJsonClass() {
		return jsonClass;
	}

	@JsonProperty("jsonClass")
	public void setJsonClass(String jsonClass) {
		this.jsonClass = jsonClass;
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
