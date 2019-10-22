package org.dimensinfin.eveonline.neocom.infinity;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

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
	public JodaModule jodaModule() {
		return new JodaModule();
	}
}
