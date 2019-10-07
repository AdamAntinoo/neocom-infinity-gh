package org.dimensinfin.eveonline.neocom.infinity.support;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.infinity.adapter.ConfigurationProviderWrapper;

@Component
public class SupportConfigurationProviderWrapper extends ConfigurationProviderWrapper {
	@Autowired
	public SupportConfigurationProviderWrapper( final String defaultValue ) throws IOException {
		super( defaultValue );
		this.configurationProperties.clear();
		this.readAllProperties();
	}

	protected void readAllProperties() {
		this.addProperty( "P.esi.tranquility.authorization.server", "http://localhost:6090" );
		this.addProperty( "P.esi.tranquility.authorization.clientid", "--cclliieennttiidd--" );
		this.addProperty( "P.esi.tranquility.authorization.secretkey", "FFTTHHIISSIISSTTHHEESSEECCRREETTFF" );
		this.addProperty( "P.esi.tranquility.authorization.callback", "http://neocominfinity.poc/app/loginValidation" );
		this.addProperty( "P.esi.tranquility.authorization.agent",
				"Dimensinfin Industries 2019 : Spring Boot 2 backend client : Development" );
		this.addProperty( "P.esi.tranquility.authorization.scopes.filename", "esiconf/ESINetworkScopes.Tranquility.txt" );
		this.addProperty( "P.esi.authorization.content.type", "application/json" );
		this.addProperty( "P.esi.data.server.location", "http://localhost:6091/" );

//		this.addProperty( "P.cache.root.storage.name", "src/test/NeoCom.UnitTest" );
//		this.addProperty( "P.cache.directory.path", "NeoComCache/" );
//		this.addProperty( "P.cache.directory.store.esiitem", "ESIData.cache.store" );
//		this.addProperty( "P.cache.esiitem.timeout", "86400" );
//		this.addProperty( "P.cache.esinetwork.filename", "ESINetworkManager.cache.store" );
//		this.addProperty( "P.esi.tranquility.authorization.server", "https://login.eveonline.com/" );
//		this.addProperty( "P.esi.tranquility.authorization.clientid", "dbc9c2b1d18d49d8adacd23436c5281d" );
//		this.addProperty( "P.esi.tranquility.authorization.secretkey", "QqnTLCqLQxZYHgHUuobkNA9g950vXVYDMg8ETTXM" );
//		this.addProperty( "P.esi.tranquility.authorization.callback", "eveauth-neocom://esiauthentication" );
//		this.addProperty( "P.esi.tranquility.authorization.agent", "org.dimensinfin.eveonline.neocom; Dimensinfin Industries; " +
//				"Data Management Unit Testing" );
//		this.addProperty( "P.esi.tranquility.authorization.scopes.filename", "esiconf/ESINetworkScopes.Tranquility.txt" );

	}

	protected void addProperty( final String key, final String value ) {
		this.configurationProperties.setProperty( key, value );
	}
}
