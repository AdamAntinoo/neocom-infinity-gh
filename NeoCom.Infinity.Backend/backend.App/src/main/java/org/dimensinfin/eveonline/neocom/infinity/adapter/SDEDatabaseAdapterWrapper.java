package org.dimensinfin.eveonline.neocom.infinity.adapter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.adapter.implementers.SBSDEDatabaseAdapter;

@Component
public class SDEDatabaseAdapterWrapper extends SBSDEDatabaseAdapter {
	private String databasePath;
	private String databaseName;

	// - C O N S T R U C T O R S
	@Autowired
	public SDEDatabaseAdapterWrapper( @Value("${neocom.sde.database.path}") final String databasePath,
	                                  @Value("${neocom.sde.database.name}") final String databaseName,
	                                  final FileSystemWrapper fileSystemAdapter ) {
		this.databasePath = databasePath;
		this.databaseName = databaseName;
		this.fileSystemAdapter = fileSystemAdapter;
	}

	@PostConstruct
	private void build() {
		new SBSDEDatabaseAdapter.Builder( this )
				.withDatabasePath( this.databasePath )
				.withDatabaseName( this.databaseName )
				.withFileSystemAdapter( this.fileSystemAdapter )
				.build();
	}
}