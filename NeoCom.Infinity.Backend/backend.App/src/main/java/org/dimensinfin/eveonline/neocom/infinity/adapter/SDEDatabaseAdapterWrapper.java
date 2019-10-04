package org.dimensinfin.eveonline.neocom.infinity.adapter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.adapter.implementers.SDEDatabaseAdapter;

@Component
public class SDEDatabaseAdapterWrapper extends SDEDatabaseAdapter {
	private String databasePath;
	private String databaseName;

	// - C O N S T R U C T O R S
	@Autowired
	public SDEDatabaseAdapterWrapper( @Value("${neocom.database.neocom.databasehost}") final String databaseHost,
	                                  @Value("${neocom.database.neocom.databasepath}") final String databasePath,
	                                  final FileSystemWrapper fileSystemAdapter ) {
		this.databasePath = databaseHost;
		this.databaseName = databasePath;
		this.fileSystemAdapter = fileSystemAdapter;
	}

	@PostConstruct
	private void build() {
		new SDEDatabaseAdapter.Builder( this )
				.withDatabasePath( this.databasePath )
				.withDatabaseName( this.databaseName )
				.withFileSystemAdapter( this.fileSystemAdapter )
				.build();
	}
}