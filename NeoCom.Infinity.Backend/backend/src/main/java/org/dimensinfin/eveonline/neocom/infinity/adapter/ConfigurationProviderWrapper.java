package org.dimensinfin.eveonline.neocom.infinity.adapter;

import org.springframework.stereotype.Component;

@Component
public class ConfigurationProviderWrapper extends SBConfigurationProvider {
	private static final String DEFAULT_PROPERTIES_DIRECTORY = "properties";

	public ConfigurationProviderWrapper() {
		super(DEFAULT_PROPERTIES_DIRECTORY);
	}

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
