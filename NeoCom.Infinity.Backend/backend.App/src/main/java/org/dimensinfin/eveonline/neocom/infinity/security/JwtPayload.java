package org.dimensinfin.eveonline.neocom.infinity.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtPayload {
	private String sub;
	private String iss;

	private String uniqueId;
	private String accountName;
	private Integer corporationId;
	private Integer pilotId;

	public Integer getCorporationId() {
		return this.corporationId;
	}

	public Integer getPilotId() {
		return this.pilotId;
	}
}