package org.dimensinfin.eveonline.neocom.infinity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.LOGIN_VERIFICATION_EXTENDED_URL;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.LOGIN_VERIFICATION_URL;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.SERVER_STATUS_URL;

//@Configuration
//@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
	private NeoComAuthenticationProvider neoComAuthenticationProvider;
	private CredentialDetailsService credentialDetailsService;

	@Autowired
	public ApplicationSecurityConfig( final CredentialDetailsService userDetailsService,
	                                  final NeoComAuthenticationProvider neoComAuthenticationProvider ) {
		this.neoComAuthenticationProvider = neoComAuthenticationProvider;
		this.credentialDetailsService = userDetailsService;
	}

	@Override
	protected void configure( HttpSecurity http ) throws Exception {
		http
				.cors()
				.and().csrf().disable()
//				.authorizeRequests()
//				.antMatchers( LOGIN_VERIFICATION_URL, LOGIN_VERIFICATION_EXTENDED_URL, SERVER_STATUS_URL ).permitAll()
//				.anyRequest().authenticated()
//				.and().addFilter( new JWTAuthorizationFilter( authenticationManager() ) )
				// This disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
	}

	@Override
	public void configure( WebSecurity web ) throws Exception {
		web.ignoring()
				.antMatchers( LOGIN_VERIFICATION_URL, SERVER_STATUS_URL, LOGIN_VERIFICATION_EXTENDED_URL );
	}

	@Override
	public void configure( AuthenticationManagerBuilder auth ) throws Exception {
		auth.authenticationProvider( this.neoComAuthenticationProvider );
	}

//	@Bean
//	CorsConfigurationSource corsConfigurationSource() {
//		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration( "/**", new CorsConfiguration().applyPermitDefaultValues() );
//		return source;
//	}
}
