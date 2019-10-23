package org.dimensinfin.eveonline.neocom.infinity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.LOGIN_VERIFICATION_URL;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.SERVER_STATUS_URL;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConf extends WebSecurityConfigurerAdapter {
	private NeoComAuthenticationProvider neoComAuthenticationProvider;
	private CredentialDetailsService credentialDetailsService;

	@Autowired
	public ApplicationSecurityConf( final CredentialDetailsService userDetailsService,
	                                final NeoComAuthenticationProvider neoComAuthenticationProvider/*,
	                            final BCryptPasswordEncoder bCryptPasswordEncoder*/ ) {
		this.neoComAuthenticationProvider = neoComAuthenticationProvider;
		this.credentialDetailsService = userDetailsService;//		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure( HttpSecurity http ) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
				.antMatchers( LOGIN_VERIFICATION_URL, SERVER_STATUS_URL ).permitAll()
				.anyRequest().authenticated()
				.and()
				.addFilter( new JWTAuthorizationFilter( authenticationManager() ) )
				// This disables session creation on Spring Security
				.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS );
	}

	@Override
	public void configure( AuthenticationManagerBuilder auth ) throws Exception {
		auth.authenticationProvider( this.neoComAuthenticationProvider );
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration( "/**", new CorsConfiguration().applyPermitDefaultValues() );
		return source;
	}
}
