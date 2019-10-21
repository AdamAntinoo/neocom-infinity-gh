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
		"corporationId",
		"ceo_id",
		"ceoPilotData",
		"creator_id",
		"alliance_id",
		"alliance",
		"date_founded",
		"description",
		"home_station_id",
		"member_count",
		"name",
		"shares",
		"tax_rate",
		"ticker",
		"url",
		"url4Icon"
})
public class CorporationResponse {

	@JsonProperty("corporationId")
	private String jsonClass;
	@JsonProperty("corporationId")
	private Integer corporationId;
	@JsonProperty("ceo_id")
	private Integer ceoId;
	@JsonProperty("ceoPilotData")
	private CeoPilotData ceoPilotData;
	@JsonProperty("creator_id")
	private Integer creatorId;
	@JsonProperty("alliance_id")
	private Integer allianceId;
	@JsonProperty("alliance")
	private Alliance alliance;
	@JsonProperty("date_founded")
	private String dateFounded;
	@JsonProperty("description")
	private String description;
	@JsonProperty("home_station_id")
	private Integer homeStationId;
	@JsonProperty("member_count")
	private Integer memberCount;
	@JsonProperty("name")
	private String name;
	@JsonProperty("shares")
	private Integer shares;
	@JsonProperty("tax_rate")
	private Double taxRate;
	@JsonProperty("ticker")
	private String ticker;
	@JsonProperty("url")
	private String url;
	@JsonProperty("url4Icon")
	private String url4Icon;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("jsonClass")
	public String getJsonClass() {
		return this.jsonClass;
	}
	@JsonProperty("corporationId")
	public Integer getCorporationId() {
		return corporationId;
	}

	@JsonProperty("corporationId")
	public void setCorporationId(Integer corporationId) {
		this.corporationId = corporationId;
	}

	@JsonProperty("ceo_id")
	public Integer getCeoId() {
		return ceoId;
	}

	@JsonProperty("ceo_id")
	public void setCeoId(Integer ceoId) {
		this.ceoId = ceoId;
	}

	@JsonProperty("ceoPilotData")
	public CeoPilotData getCeoPilotData() {
		return ceoPilotData;
	}

	@JsonProperty("ceoPilotData")
	public void setCeoPilotData(CeoPilotData ceoPilotData) {
		this.ceoPilotData = ceoPilotData;
	}

	@JsonProperty("creator_id")
	public Integer getCreatorId() {
		return creatorId;
	}

	@JsonProperty("creator_id")
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	@JsonProperty("alliance_id")
	public Integer getAllianceId() {
		return allianceId;
	}

	@JsonProperty("alliance_id")
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

	@JsonProperty("date_founded")
	public String getDateFounded() {
		return dateFounded;
	}

	@JsonProperty("date_founded")
	public void setDateFounded(String dateFounded) {
		this.dateFounded = dateFounded;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	@JsonProperty("home_station_id")
	public Integer getHomeStationId() {
		return homeStationId;
	}

	@JsonProperty("home_station_id")
	public void setHomeStationId(Integer homeStationId) {
		this.homeStationId = homeStationId;
	}

	@JsonProperty("member_count")
	public Integer getMemberCount() {
		return memberCount;
	}

	@JsonProperty("member_count")
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("shares")
	public Integer getShares() {
		return shares;
	}

	@JsonProperty("shares")
	public void setShares(Integer shares) {
		this.shares = shares;
	}

	@JsonProperty("tax_rate")
	public Double getTaxRate() {
		return taxRate;
	}

	@JsonProperty("tax_rate")
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	@JsonProperty("ticker")
	public String getTicker() {
		return ticker;
	}

	@JsonProperty("ticker")
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
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
