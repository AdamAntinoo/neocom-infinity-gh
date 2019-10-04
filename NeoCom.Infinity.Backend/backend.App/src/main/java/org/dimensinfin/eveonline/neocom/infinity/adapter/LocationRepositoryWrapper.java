package org.dimensinfin.eveonline.neocom.infinity.adapter;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.database.repositories.LocationRepository;

@Component
public class LocationRepositoryWrapper extends LocationRepository {
	// - C O N S T R U C T O R S
	@Autowired
	public LocationRepositoryWrapper( final SDEDatabaseAdapterWrapper sdeDatabaseAdapter ) {
		this.sdeDatabaseAdapter = sdeDatabaseAdapter;
	}

	@PostConstruct
	private void build() {
		new LocationRepository.Builder( this )
				.withSDEDatabaseAdapter( this.sdeDatabaseAdapter )
				.build();
	}
}