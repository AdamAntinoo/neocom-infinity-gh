package org.dimensinfin.eveonline.neocom.infinity.adapter;

import com.j256.ormlite.dao.Dao;
import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.database.repositories.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class CredentialRepositoryWrapper extends CredentialRepository {
	private SBNeoComDBWrapper neocomDBAdapter;
	private Dao<Credential, String> credentialDao;

	public CredentialRepositoryWrapper(@Autowired final SBNeoComDBWrapper neocomDBAdapter) {
		this.neocomDBAdapter = neocomDBAdapter;
		this.credentialDao = this.neocomDBAdapter.getCredentialDao();
	}


//	@PostConstruct
//	private void build() {
//
//
//	}
}