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
		"allianceId",
		"ceoId",
		"creatorId",
		"dateFounded",
		"description",
		"factionId",
		"homeStationId",
		"memberCount",
		"name",
		"shares",
		"taxRate",
		"ticker",
		"url",
		"warEligible"
})
public class CorporationPublicData {

	@JsonProperty("allianceId")
	private Integer allianceId;
	@JsonProperty("ceoId")
	private Integer ceoId;
	@JsonProperty("creatorId")
	private Integer creatorId;
	@JsonProperty("dateFounded")
	private String dateFounded;
	@JsonProperty("description")
	private String description;
	@JsonProperty("factionId")
	private Object factionId;
	@JsonProperty("homeStationId")
	private Integer homeStationId;
	@JsonProperty("memberCount")
	private Integer memberCount;
	@JsonProperty("name")
	private String name;
	@JsonProperty("shares")
	private Integer shares;
	@JsonProperty("taxRate")
	private Double taxRate;
	@JsonProperty("ticker")
	private String ticker;
	@JsonProperty("url")
	private String url;
	@JsonProperty("warEligible")
	private Object warEligible;
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

	@JsonProperty("ceoId")
	public Integer getCeoId() {
		return ceoId;
	}

	@JsonProperty("ceoId")
	public void setCeoId(Integer ceoId) {
		this.ceoId = ceoId;
	}

	@JsonProperty("creatorId")
	public Integer getCreatorId() {
		return creatorId;
	}

	@JsonProperty("creatorId")
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	@JsonProperty("dateFounded")
	public String getDateFounded() {
		return dateFounded;
	}

	@JsonProperty("dateFounded")
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

	@JsonProperty("factionId")
	public Object getFactionId() {
		return factionId;
	}

	@JsonProperty("factionId")
	public void setFactionId(Object factionId) {
		this.factionId = factionId;
	}

	@JsonProperty("homeStationId")
	public Integer getHomeStationId() {
		return homeStationId;
	}

	@JsonProperty("homeStationId")
	public void setHomeStationId(Integer homeStationId) {
		this.homeStationId = homeStationId;
	}

	@JsonProperty("memberCount")
	public Integer getMemberCount() {
		return memberCount;
	}

	@JsonProperty("memberCount")
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

	@JsonProperty("taxRate")
	public Double getTaxRate() {
		return taxRate;
	}

	@JsonProperty("taxRate")
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

	@JsonProperty("warEligible")
	public Object getWarEligible() {
		return warEligible;
	}

	@JsonProperty("warEligible")
	public void setWarEligible(Object warEligible) {
		this.warEligible = warEligible;
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
