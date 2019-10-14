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
		this.configurationProvider = configurationProvider;
		logger.info( "<< [NeoComDBWrapper.<constructor>]" );
	}

	@PostConstruct
	private void build() throws SQLException {
		logger.info( ">> [NeoComDBWrapper.build]" );
		// Check if there is Heroku running configuration.
		if (!this.isHerokuConfiguration()) {
			final String databaseHost = this.configurationProvider.getResourceString( "P.database.neocom.databasehost" );
			final String databasePath = this.configurationProvider.getResourceString( "P.database.neocom.databasepath" );
			final String user = this.configurationProvider.getResourceString( "P.database.neocom.databaseuser" );
			final String password = this.configurationProvider.getResourceString( "P.database.neocom.databasepassword" );
			final SBNeoComDBAdapter adapter = new Builder( this )
					.withDatabaseHostName( databaseHost )
					.withDatabasePath( databasePath )
					.withDatabaseUser( user )
					.withDatabasePassword( password )
					.optionalDatabaseType( this.configurationProvider.getResourceString( "P.database.neocom.databasetype" ) )
					.optionalDatabaseOptions(
							this.configurationProvider.getResourceString( "P.database.neocom.databaseoptions" ) )
					.build();
			adapter.onCreate( adapter.getConnectionSource() );
			logger.info( "<< [NeoComDBWrapper.build]" );
		}
	}

	private boolean isHerokuConfiguration() throws SQLException {
		final String neocomUrl = System.getenv( "JDBC_DATABASE_URL" );
		if (null != neocomUrl) {
			final SBNeoComDBAdapter adapter = new SBNeoComDBAdapter.Builder( this )
					.optionalDatabaseType( "postgres" )
					.optionalDatabaseUrl( neocomUrl )
					.build();
			adapter.onCreate( adapter.getConnectionSource() );
			return true;
		}
		return false;
	}
}
