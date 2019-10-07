package org.dimensinfin.eveonline.neocom.infinity.adapter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.adapter.implementers.SBConfigurationProvider;

@Component
public class ConfigurationProviderWrapper extends SBConfigurationProvider {
	private static String DEFAULT_PROPERTIES_DIRECTORY = "properties";
//	@Value("${P.runtime.configuration.properties.path}")
//	private String cardTopic = DEFAULT_PROPERTIES_DIRECTORY;

	@Autowired
	public ConfigurationProviderWrapper(@Value("${P.runtime.configuration.properties.path}") final String configuredLocation) throws IOException {
		this.setConfiguredPropertiesDirectory( configuredLocation );
//		try {
			this.readAllProperties();
//		} catch (final IOException ) {
//			e.printStackTrace();
//		}
	}

	public boolean getResourceBoolean( final String key ) {
		final String value = this.getResourceString( key, "false" );
		if (value.equalsIgnoreCase( "true" )) return true;
		if (value.equalsIgnoreCase( "on" )) return true;
		if (value.equalsIgnoreCase( "0" )) return false;
		return false;
	}
}
