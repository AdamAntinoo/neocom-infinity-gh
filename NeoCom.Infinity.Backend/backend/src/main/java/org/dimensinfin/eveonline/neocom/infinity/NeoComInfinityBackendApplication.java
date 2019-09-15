package org.dimensinfin.eveonline.neocom.infinity;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing
@SpringBootApplication
public class NeoComInfinityBackendApplication {
	private static final Logger logger = LoggerFactory.getLogger(NeoComInfinityBackendApplication.class);
	private static ObjectMapper jsonMapper = new ObjectMapper();
	private static SimpleModule neocomSerializerModule;


	public static void registerSerializer( final Class classReference, final JsonSerializer serializer ) {
		logger.info("-- [NeoComInfinityBackendApplication.main]> Registering serializer for {}.",
		            classReference.getSimpleName());
		neocomSerializerModule.addSerializer(classReference, serializer);
	}

	public static void main( String[] args ) {
		logger.info(">> [NeoComInfinityBackendApplication.main]");
		logger.info("-- [NeoComInfinityBackendApplication.main]> Create serializer container...");
		jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
		jsonMapper.registerModule(new JodaModule());
		// Add our own serializer modeule where to register the application custom serializers.
		neocomSerializerModule = new SimpleModule();
		jsonMapper.registerModule(neocomSerializerModule);

		SpringApplication.run(NeoComInfinityBackendApplication.class, args);
		logger.info("<< [NeoComInfinityBackendApplication.main]");
	}
}
