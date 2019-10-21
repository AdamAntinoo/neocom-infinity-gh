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
		"description",
		"iconId",
		"id",
		"name",
		"shortDescription"
})
public class Ancestry {

	@JsonProperty("bloodlineId")
	private Integer bloodlineId;
	@JsonProperty("description")
	private String description;
	@JsonProperty("iconId")
	private Integer iconId;
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("shortDescription")
	private String shortDescription;
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

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("iconId")
	public Integer getIconId() {
		return iconId;
	}

	@JsonProperty("iconId")
	public void setIconId(Integer iconId) {
		this.iconId = iconId;
	}

	@JsonProperty("id")
	public Integer getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(Integer id) {
		this.id = id;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("shortDescription")
	public String getShortDescription() {
		return shortDescription;
	}

	@JsonProperty("shortDescription")
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
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
