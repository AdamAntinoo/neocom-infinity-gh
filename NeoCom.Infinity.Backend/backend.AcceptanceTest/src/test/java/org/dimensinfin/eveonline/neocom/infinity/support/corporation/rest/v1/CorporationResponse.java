package org.dimensinfin.eveonline.neocom.infinity.support.corporation.rest.v1;

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
		"jsonClass",
		"corporationId",
		"corporationPublicData",
		"ceoPilotData",
		"allianceId",
		"alliance",
		"url4Icon"
})
public class CorporationResponse {

	@JsonProperty("jsonClass")
	private String jsonClass;
	@JsonProperty("corporationId")
	private Integer corporationId;
	@JsonProperty("corporationPublicData")
	private CorporationPublicData corporationPublicData;
	@JsonProperty("ceoPilotData")
	private CeoPilotData ceoPilotData;
	@JsonProperty("allianceId")
	private Integer allianceId;
	@JsonProperty("alliance")
	private Alliance alliance;
	@JsonProperty("url4Icon")
	private String url4Icon;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("jsonClass")
	public String getJsonClass() {
		return jsonClass;
	}

	@JsonProperty("jsonClass")
	public void setJsonClass(String jsonClass) {
		this.jsonClass = jsonClass;
	}

	@JsonProperty("corporationId")
	public Integer getCorporationId() {
		return corporationId;
	}

	@JsonProperty("corporationId")
	public void setCorporationId(Integer corporationId) {
		this.corporationId = corporationId;
	}

	@JsonProperty("corporationPublicData")
	public CorporationPublicData getCorporationPublicData() {
		return corporationPublicData;
	}

	@JsonProperty("corporationPublicData")
	public void setCorporationPublicData(CorporationPublicData corporationPublicData) {
		this.corporationPublicData = corporationPublicData;
	}

	@JsonProperty("ceoPilotData")
	public CeoPilotData getCeoPilotData() {
		return ceoPilotData;
	}

	@JsonProperty("ceoPilotData")
	public void setCeoPilotData(CeoPilotData ceoPilotData) {
		this.ceoPilotData = ceoPilotData;
	}

	@JsonProperty("allianceId")
	public Integer getAllianceId() {
		return allianceId;
	}

	@JsonProperty("allianceId")
	public void setAllianceId(Integer allianceId) {
		this.allianceId = allianceId;
	}

	@JsonProperty("alliance")
	public Alliance getAlliance() {
		return alliance;
	}

	@JsonProperty("alliance")
	public void setAlliance(Alliance alliance) {
		this.alliance = alliance;
	}

	@JsonProperty("url4Icon")
	public String getUrl4Icon() {
		return url4Icon;
	}

	@JsonProperty("url4Icon")
	public void setUrl4Icon(String url4Icon) {
		this.url4Icon = url4Icon;
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
