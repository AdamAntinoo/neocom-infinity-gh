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
		"creator_corporation_id",
		"creator_id",
		"date_founded",
		"executor_corporation_id",
		"name",
		"ticker"
})
public class Alliance {

	@JsonProperty("creator_corporation_id")
	private Integer creatorCorporationId;
	@JsonProperty("creator_id")
	private Integer creatorId;
	@JsonProperty("date_founded")
	private String dateFounded;
	@JsonProperty("executor_corporation_id")
	private Integer executorCorporationId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("ticker")
	private String ticker;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("creator_corporation_id")
	public Integer getCreatorCorporationId() {
		return creatorCorporationId;
	}

	@JsonProperty("creator_corporation_id")
	public void setCreatorCorporationId(Integer creatorCorporationId) {
		this.creatorCorporationId = creatorCorporationId;
	}

	@JsonProperty("creator_id")
	public Integer getCreatorId() {
		return creatorId;
	}

	@JsonProperty("creator_id")
	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	@JsonProperty("date_founded")
	public String getDateFounded() {
		return dateFounded;
	}

	@JsonProperty("date_founded")
	public void setDateFounded(String dateFounded) {
		this.dateFounded = dateFounded;
	}

	@JsonProperty("executor_corporation_id")
	public Integer getExecutorCorporationId() {
		return executorCorporationId;
	}

	@JsonProperty("executor_corporation_id")
	public void setExecutorCorporationId(Integer executorCorporationId) {
		this.executorCorporationId = executorCorporationId;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("ticker")
	public String getTicker() {
		return ticker;
	}

	@JsonProperty("ticker")
	public void setTicker(String ticker) {
		this.ticker = ticker;
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
