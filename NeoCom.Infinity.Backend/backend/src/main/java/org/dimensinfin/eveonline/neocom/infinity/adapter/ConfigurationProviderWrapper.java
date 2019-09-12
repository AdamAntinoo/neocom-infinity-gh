package org.dimensinfin.eveonline.neocom.infinity.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationProviderWrapper extends SBConfigurationProvider {
	@Value("${P.runtime.configuration.properties.path}")
	private static String DEFAULT_PROPERTIES_DIRECTORY = "properties";

	public ConfigurationProviderWrapper() {
		super(DEFAULT_PROPERTIES_DIRECTORY);
	}

//	@PostConstruct
//	private void build() {
//		// Configure the provider propeties location.
//		this.
//
//
//	}

//	public ConfigurationProviderWrapper(final String propertiesDirectory) {
//		super(propertiesDirectory);
//	}

//    public static class Builder {
//        private ConfigurationProviderComponent onConstruction;
//
//        public Builder() {
//            this.onConstruction = new ConfigurationProviderComponent();
//        }
//    }
}
