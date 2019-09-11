package org.dimensinfin.eveonline.neocom.infinity.adapters;

import org.springframework.stereotype.Component;

@Component
public class ConfigurationProviderComponent extends SBConfigurationProvider {
	private static final String DEFAULT_PROPERTIES_DIRECTORY = "properties";

	public ConfigurationProviderComponent() {
		super(DEFAULT_PROPERTIES_DIRECTORY);
	}

	public ConfigurationProviderComponent(final String propertiesDirectory) {
		super(propertiesDirectory);
	}

//    public static class Builder {
//        private ConfigurationProviderComponent onConstruction;
//
//        public Builder() {
//            this.onConstruction = new ConfigurationProviderComponent();
//        }
//    }
}
