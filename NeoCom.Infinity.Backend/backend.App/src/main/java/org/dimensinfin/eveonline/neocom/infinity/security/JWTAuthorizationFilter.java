package org.dimensinfin.eveonline.neocom.infinity.security;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.AUTHORIZATION_HEADER_STRING;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.SECRET;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.SUBJECT;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
	@Autowired
	public JWTAuthorizationFilter( final AuthenticationManager authManager ) {
		super( authManager );
	}

	@Override
	protected void doFilterInternal( HttpServletRequest req,
	                                 HttpServletResponse res,
	                                 FilterChain chain ) throws IOException, ServletException {
		String meth = req.getMethod();
		final String header = req.getHeader( AUTHORIZATION_HEADER_STRING );
		if (header == null || !header.startsWith( TOKEN_PREFIX )) { // The expected token is not found
			logger.info( "[AUTHORIZATION]> Request not authorized because 'Authorization' header not found." );
			chain.doFilter( req, res );
			return;
		}
		final UsernamePasswordAuthenticationToken authentication = this.getAuthentication( req );
		if (null != authentication) SecurityContextHolder.getContext().setAuthentication( authentication );
		chain.doFilter( req, res );
	}

	private UsernamePasswordAuthenticationToken getAuthentication( final HttpServletRequest request ) {
		try {
			final String token = request.getHeader( AUTHORIZATION_HEADER_STRING );
//		if (token != null) {
			final DecodedJWT jwtToken = JWT.require( Algorithm.HMAC512( SECRET.getBytes() ) )
					.build()
					.verify( token.replace( TOKEN_PREFIX, "" ) );
			if (this.validateSubject( token ))  // Check this is the subject we expect
				return new UsernamePasswordAuthenticationToken( jwtToken.getPayload(), null, new ArrayList<>() );
			else
				logger.info( "[AUTHORIZATION]> Expected JWT sub does not match with authorized message." );
		} catch (final RuntimeException rte) {
			logger.info( "[AUTHORIZATION]> Token validation failed. " + rte.getMessage() );
			return null;
		}
		return null;
	}

	private boolean validateSubject( final String token ) {
		final String subject = JWT.require( Algorithm.HMAC512( SECRET.getBytes() ) )
				.build()
				.verify( token.replace( TOKEN_PREFIX, "" ) )
				.getSubject();

		if (null != subject)
			if (subject.equalsIgnoreCase( SUBJECT )) return true;
		return false;
	}
}
