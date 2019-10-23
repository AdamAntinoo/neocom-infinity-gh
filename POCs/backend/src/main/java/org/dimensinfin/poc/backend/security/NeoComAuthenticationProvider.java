package org.dimensinfin.poc.backend.security;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class NeoComAuthenticationProvider {
	public Integer getAuthenticatedCorporation( final int cid ) throws IOException {
		return cid;
	}

	public Integer getAuthenticatedPilot( final int pilotId ) {
		return pilotId;
	}
}