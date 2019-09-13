package org.dimensinfin.eveonline.neocom.infinity.adapter;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class ConfigurationProviderWrapper extends SBConfigurationProvider {
	private static String DEFAULT_PROPERTIES_DIRECTORY = "properties";
//	@Value("${P.runtime.configuration.properties.path}")
	private String configuredPropertyDirectory;

	public ConfigurationProviderWrapper() {
		super(DEFAULT_PROPERTIES_DIRECTORY);
	}

	@PostConstruct
	private void build() throws IOException {
		if (null != this.configuredPropertyDirectory) this.setPropertiesDirectory(configuredPropertyDirectory);
		this.readAllProperties();
	}
}
