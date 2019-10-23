package org.dimensinfin.eveonline.neocom.infinity.security;

import java.io.IOException;

import org.springframework.stereotype.Component;

@Component
public class NeoComAuthenticationProvider {
	public Integer getAuthenticatedCorporation( final int cid ) throws IOException {
//		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		final String payloadBase64 = (String) authentication.getPrincipal();
//		final String payloadString = new String( Base64.decodeBase64( payloadBase64.getBytes() ) );
//		final JwtPayload payload = jsonMapper.readValue( payloadString, JwtPayload.class );
//		if (null == payload) throw new NeoComSBException( ErrorInfo.CORPORATION_ID_NOT_AUTHORIZED );
		return cid;
	}

	public Integer getAuthenticatedPilot( final int pilotId ) {
		return pilotId;
	}
//	// - B U I L D E R
//	public static class Builder {
//		private NeoComAuthenticationProvider onConstruction;
//
//		public Builder() {
//			this.onConstruction = new NeoComAuthenticationProvider();
//		}
//
//		public NeoComAuthenticationProvider build() {
//			return this.onConstruction;
//		}
//	}
}