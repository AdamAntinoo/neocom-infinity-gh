package org.dimensinfin.eveonline.neocom.infinity.adapter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.adapters.ESIDataAdapter;
import org.dimensinfin.eveonline.neocom.adapters.LocationCatalogService;

@Component
public class ESIDataAdapterWrapper extends ESIDataAdapter {
	// - C O N S T R U C T O R S
	@Autowired
	public ESIDataAdapterWrapper( final ConfigurationProviderWrapper configurationProvider,
	                              final FileSystemWrapper fileSystemAdapter,
	                              final LocationCatalogService locationCatalogService ) {
		this.configurationProvider = configurationProvider;
		this.fileSystemAdapter = fileSystemAdapter;
		this.locationCatalogService = locationCatalogService;
	}

	@PostConstruct
	private void build() {
		new ESIDataAdapter.Builder( this )
				.withConfigurationProvider( this.configurationProvider )
				.withFileSystemAdapter( this.fileSystemAdapter )
				.withLocationCatalogService( this.locationCatalogService )
				.build();
//		builder.onConstruction = this;

//		this.cacheManager = new StoreCacheManager.Builder()
//				.withEsiDataAdapter( this )
//				.withConfigurationProvider( this.configurationProvider )
//				.withFileSystem( this.fileSystemAdapter )
//				.build();
//		this.retrofitFactory = new NeoComRetrofitFactory.Builder( this.configurationProvider, this.fileSystemAdapter )
//				.build();
	}
}