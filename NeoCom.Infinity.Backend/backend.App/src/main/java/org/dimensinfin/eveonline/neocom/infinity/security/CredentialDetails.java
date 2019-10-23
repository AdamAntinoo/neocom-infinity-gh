package org.dimensinfin.eveonline.neocom.infinity.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;

public class CredentialDetails implements UserDetails {
	private Credential credential;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}

	// - B U I L D E R
	public static class Builder {
		private CredentialDetails onConstruction;

		public Builder() {
			this.onConstruction = new CredentialDetails();
		}

		public CredentialDetails.Builder withCredential( final Credential credential ) {
			this.onConstruction.credential = credential;
			return this;
		}

		public CredentialDetails build() {
			return this.onConstruction;
		}
	}
}