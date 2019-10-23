package org.dimensinfin.poc.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
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
public class BackendApplication {
    private static final Logger logger = LoggerFactory.getLogger( BackendApplication.class );

    public static void main( String[] args ) {
        logger.info( ">> [NeoComInfinityBackendApplication.main]" );
        SpringApplication.run( BackendApplication.class, args );
        logger.info( "<< [NeoComInfinityBackendApplication.main]" );
    }

//    @Bean
//    public JodaModule jodaModule() {
//        return new JodaModule();
//    }
}