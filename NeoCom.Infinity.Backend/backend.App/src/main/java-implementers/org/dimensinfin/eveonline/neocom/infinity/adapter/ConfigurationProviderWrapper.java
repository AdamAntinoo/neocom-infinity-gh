package org.dimensinfin.eveonline.neocom.infinity.adapter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.adapter.implementers.SBConfigurationProvider;

@Component
public class ConfigurationProviderWrapper extends SBConfigurationProvider {
	private static String DEFAULT_PROPERTIES_DIRECTORY = "properties";

	@Autowired
	public ConfigurationProviderWrapper( @Value("${P.runtime.configuration.properties.path}") final String configuredLocation ) throws IOException {
		this.setConfiguredPropertiesDirectory( configuredLocation );
		this.readAllProperties();
	}
}
