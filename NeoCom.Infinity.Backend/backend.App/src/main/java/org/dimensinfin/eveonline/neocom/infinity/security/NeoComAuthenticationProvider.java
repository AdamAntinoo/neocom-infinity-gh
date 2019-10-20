package org.dimensinfin.eveonline.neocom.infinity.security;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.core.ErrorInfo;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComAuthorizationException;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComSBException;

@Component
public class NeoComAuthenticationProvider implements AuthenticationProvider {
	public static final ObjectMapper jsonMapper = new ObjectMapper();

	@Override
	public Authentication authenticate( Authentication authentication ) throws AuthenticationException {
		return new UsernamePasswordAuthenticationToken( authentication.getPrincipal(), null, new ArrayList<>() );
	}

	@Override
	public boolean supports( Class<?> authentication ) {
		return authentication.equals( UsernamePasswordAuthenticationToken.class );
	}

	public Integer getAuthenticatedCorporation() throws IOException {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		final String payloadBase64 = (String) authentication.getPrincipal();
		final String payloadString = new String( Base64.decodeBase64( payloadBase64.getBytes() ) );
		final JwtPayload payload = jsonMapper.readValue( payloadString, JwtPayload.class );
		if (null == payload) throw new NeoComAuthorizationException( ErrorInfo.CORPORATION_ID_NOT_AUTHORIZED );
		return payload.getCorporationId();
	}

	public Integer getAuthenticatedPilot() {
		return this.accessDecodedPayload( ErrorInfo.PILOT_ID_NOT_AUTHORIZED ).getPilotId();
	}

	private JwtPayload accessDecodedPayload( final ErrorInfo configuredError ) {
		try {
			final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			final String payloadBase64 = (String) authentication.getPrincipal();
			final String payloadString = new String( Base64.decodeBase64( payloadBase64.getBytes() ) );
			final JwtPayload payload = jsonMapper.readValue( payloadString, JwtPayload.class );
			if (null == payload) throw new NeoComSBException( configuredError );
			return payload;
		} catch (final IOException ioe) {
			throw new NeoComSBException( configuredError, ioe );
		}
	}
}
