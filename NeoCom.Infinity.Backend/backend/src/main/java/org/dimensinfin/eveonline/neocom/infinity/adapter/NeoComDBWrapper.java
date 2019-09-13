package org.dimensinfin.eveonline.neocom.infinity.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NeoComDBWrapper extends SBNeoComDBAdapter {
	private ConfigurationProviderWrapper configurationProvider;

	public NeoComDBWrapper(@Autowired final ConfigurationProviderWrapper configurationProvider) {
		logger.info(">> [NeoComDBWrapper.<constructor>]");
		this.configurationProvider = configurationProvider;
		// Initialize the parent adapter with configuration data.
		this.localConnectionDescriptor = this.configurationProvider.getResourceString("P.database.neocom.databasehost")
				.concat("/")
				.concat(this.configurationProvider.getResourceString("P.database.neocom.databasepath"))
				.concat("?user=").concat(this.configurationProvider.getResourceString("P.database.neocom.databaseuser"))
				.concat("&password=").concat(this.configurationProvider.getResourceString("P.database.neocom.databasepassword"))
				.concat(this.configurationProvider.getResourceString("P.database.neocom.databaseoptions"));
		logger.info("<< [NeoComDBWrapper.<constructor>]");
	}
}