package org.dimensinfin.eveonline.neocom.infinity.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NeoComDBWrapper extends SBNeoComDBAdapter {
	private ConfigurationProviderWrapper configurationProvider;

	public NeoComDBWrapper(@Autowired final ConfigurationProviderWrapper configurationProvider) {
		this.configurationProvider = configurationProvider;
		// Initialize the parent adapter with configuration data.
		this.localConnectionDescriptor = this.configurationProvider.getResourceString("P.database.neocom.databasehost")
				.concat(this.configurationProvider.getResourceString("P.database.neocom.databasetype"))
				.concat(this.configurationProvider.getResourceString("P.database.neocom.databasepath"))
				.concat(this.configurationProvider.getResourceString("P.database.neocom.databaseuser"))
				.concat(this.configurationProvider.getResourceString("P.database.neocom.databasepassword"))
				.concat(this.configurationProvider.getResourceString("P.database.neocom.databaseoptions"));
	}
}