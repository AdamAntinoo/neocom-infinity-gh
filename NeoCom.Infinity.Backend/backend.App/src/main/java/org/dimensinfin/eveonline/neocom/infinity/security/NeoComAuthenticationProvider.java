package org.dimensinfin.eveonline.neocom.infinity.security;

import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class NeoComAuthenticationProvider implements AuthenticationProvider {

	@Override
	public Authentication authenticate( Authentication authentication ) throws AuthenticationException {
		return new UsernamePasswordAuthenticationToken( authentication.getPrincipal(), null, new ArrayList<>() );
	}

	@Override
	public boolean supports( Class<?> authentication ) {
		return authentication.equals( UsernamePasswordAuthenticationToken.class );
	}
}
