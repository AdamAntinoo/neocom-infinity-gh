package org.dimensinfin.eveonline.neocom.infinity.adapter;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.dimensinfin.eveonline.neocom.database.repositories.CredentialRepository;

@Component
public class CredentialRepositoryWrapper extends CredentialRepository {
	@Autowired
	public CredentialRepositoryWrapper( final NeoComDBWrapper neocomDBAdapter ) throws SQLException {
		this.credentialDao = neocomDBAdapter.getCredentialDao();
	}
}
