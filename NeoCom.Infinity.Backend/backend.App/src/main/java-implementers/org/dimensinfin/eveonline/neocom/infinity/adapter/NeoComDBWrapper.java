package org.dimensinfin.eveonline.neocom.infinity.adapter;

import java.sql.SQLException;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.adapter.implementers.SBNeoComDBAdapter;

@Component
public class NeoComDBWrapper extends SBNeoComDBAdapter {
	private ConfigurationProviderWrapper configurationProvider;

	@Autowired
	public NeoComDBWrapper( final ConfigurationProviderWrapper configurationProvider ) {
		logger.info( ">> [NeoComDBWrapper.<constructor>]" );
		this.configurationProvider=configurationProvider;
		logger.info( "<< [NeoComDBWrapper.<constructor>]" );
	}

	@PostConstruct
	private void build() throws SQLException {
		final String databaseHost = this.configurationProvider.getResourceString( "P.database.neocom.databasehost" );
		final String databasePath = this.configurationProvider.getResourceString( "P.database.neocom.databasepath" );
		final String user = this.configurationProvider.getResourceString( "P.database.neocom.databaseuser" );
		final String password = this.configurationProvider.getResourceString( "P.database.neocom.databasepassword" );
		// Initialize the parent adapter with configuration data.
//		this.localConnectionDescriptor = databaseHost
//				.concat( "/" )
//				.concat( databasePath )
//				.concat( "?user=" ).concat( user )
//				.concat( "&password=" ).concat( password );
		new SBNeoComDBAdapter.Builder( this )
				.withDatabaseHostName( databaseHost )
				.withDatabasePath( databasePath )
				.withDatabaseUser( user )
				.withDatabasePassword( password )
				.optionalDatabaseType( this.configurationProvider.getResourceString( "P.database.neocom.databasetype" ) )
				.optionalDatabaseOptions( this.configurationProvider.getResourceString( "P.database.neocom.databaseoptions" ) )
				.build();
	}
}
