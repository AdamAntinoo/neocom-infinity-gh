package org.dimensinfin.eveonline.neocom.infinity.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.adapter.implementers.SBNeoComDBAdapter;

@Component
public class NeoComDBWrapper extends SBNeoComDBAdapter {
	private ConfigurationProviderWrapper configurationProvider;

	public NeoComDBWrapper( @Autowired final ConfigurationProviderWrapper configurationProvider ) {
		logger.info(">> [NeoComDBWrapper.<constructor>]");
		this.configurationProvider = configurationProvider;
		// Initialize the parent adapter with configuration data.
		this.localConnectionDescriptor = this.configurationProvider.getResourceString("P.database.neocom.databasehost")
				                                 .concat("/")
				                                 .concat(this.configurationProvider
						                                         .getResourceString("P.database.neocom.databasepath"))
				                                 .concat("?user=").concat(
						this.configurationProvider.getResourceString("P.database.neocom.databaseuser"))
				                                 .concat("&password=").concat(
						this.configurationProvider.getResourceString("P.database.neocom.databasepassword"));
		final String options = this.configurationProvider.getResourceString("P.database.neocom.databaseoptions");
		if (null != options)
			if (!options.isEmpty()) this.localConnectionDescriptor.concat(options);
		logger.info("<< [NeoComDBWrapper.<constructor>]");
	}
}
