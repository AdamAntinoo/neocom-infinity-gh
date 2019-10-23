package org.dimensinfin.eveonline.neocom.infinity.security;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.database.repositories.CredentialRepository;
import org.dimensinfin.eveonline.neocom.infinity.core.ExceptionMessagesExternalisedType;

@Service
public class CredentialDetailsService implements UserDetailsService {
	private CredentialRepository credentialRepository;

	@Autowired
	public CredentialDetailsService( final CredentialRepository credentialRepository ) {
		this.credentialRepository = credentialRepository;
	}

	@Override
	public UserDetails loadUserByUsername( final String uniqueId ) throws UsernameNotFoundException {
		try {
			final Credential credential = this.credentialRepository.findCredentialById( uniqueId );
			if (credential == null)
				throw new UsernameNotFoundException(
						ExceptionMessagesExternalisedType.CREDENTIAL_NOT_FOUND.getMessage( uniqueId ) );
			return new CredentialDetails.Builder()
					.withCredential( credential )
					.build();
		} catch (SQLException e) {
			throw new UsernameNotFoundException(
					ExceptionMessagesExternalisedType.CREDENTIAL_NOT_FOUND_BECAUSE_EXCEPTION.getMessage() );
		}
	}
}
