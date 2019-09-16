package org.dimensinfin.eveonline.neocom.infinity.adapter;

import com.j256.ormlite.dao.Dao;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.database.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
public class CredentialRepositoryWrapper extends CredentialRepository {
	private NeoComDBWrapper neocomDBAdapter;

	@Autowired
	public CredentialRepositoryWrapper( final NeoComDBWrapper neocomDBAdapter ) throws SQLException {
		this.neocomDBAdapter = neocomDBAdapter;
		this.credentialDao = this.neocomDBAdapter.getCredentialDao();
	}
}
