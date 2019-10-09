package org.dimensinfin.eveonline.neocom.infinity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class NeoComInfinityBackendApplication {
	private static final Logger logger = LoggerFactory.getLogger( NeoComInfinityBackendApplication.class );

	public static void main( String[] args ) {
		logger.info( ">> [NeoComInfinityBackendApplication.main]" );
		SpringApplication.run( NeoComInfinityBackendApplication.class, args );
		logger.info( "<< [NeoComInfinityBackendApplication.main]" );
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
