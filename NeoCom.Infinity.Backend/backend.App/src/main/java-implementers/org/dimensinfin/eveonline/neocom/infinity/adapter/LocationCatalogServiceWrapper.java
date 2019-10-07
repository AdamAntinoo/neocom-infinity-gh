package org.dimensinfin.eveonline.neocom.infinity.adapter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.adapters.LocationCatalogService;

@Component
public class LocationCatalogServiceWrapper extends LocationCatalogService {
	// - C O N S T R U C T O R S
	@Autowired
	public LocationCatalogServiceWrapper( final ConfigurationProviderWrapper configurationProvider,
	                                      final FileSystemWrapper fileSystemAdapter,
//	                                      final SDEDatabaseAdapterWrapper sdeDatabaseAdapter,
	                                      final LocationRepositoryWrapper locationRepository ) {
		this.configurationProvider = configurationProvider;
		this.fileSystemAdapter = fileSystemAdapter;
//		this.sdeDatabaseAdapter = sdeDatabaseAdapter;
		this.locationRepository = locationRepository;
	}

	@PostConstruct
	private void build() {
		new LocationCatalogService.Builder( this )
				.withConfigurationProvider( this.configurationProvider )
				.withFileSystemAdapter( this.fileSystemAdapter )
//				.withSDEDatabaseAdapter( this.sdeDatabaseAdapter )
				.withLocationRepository( this.locationRepository )
				.build();
	}
}