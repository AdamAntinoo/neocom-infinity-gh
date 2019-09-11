package org.dimensinfin.eveonline.neocom.infinity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Objects;

//@EnableScheduling
//@EnableJpaAuditing
//@Configuration(value="NeoComInfinityBackendApplication")
@SpringBootApplication
public class NeoComInfinityBackendApplication {
    private static final Logger logger = LoggerFactory.getLogger(NeoComInfinityBackendApplication.class);
    private static final NeoComComponentFactory neocomComponentFactory = new NeoComComponentFactory();

    public static void main(String[] args) {
		logger.info(">> [NeoComInfinityBackendApplication.main]");
		logger.info(">> [NeoComInfinityBackendApplication.main]> Check application startup...");
		Objects.requireNonNull(neocomComponentFactory);
        SpringApplication.run(NeoComInfinityBackendApplication.class, args);
        logger.info("<< [NeoComInfinityBackendApplication.main]");
    }
}
