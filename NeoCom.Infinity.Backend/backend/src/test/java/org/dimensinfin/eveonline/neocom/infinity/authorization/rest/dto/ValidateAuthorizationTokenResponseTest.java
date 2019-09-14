package org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ValidateAuthorizationTokenResponseTest {
	private Credential credential;

	@Before
	public void setUp() {
		this.credential = Mockito.mock(Credential.class);
	}

	@Test
	public void assertContract() {
		final ValidateAuthorizationTokenResponse request = new ValidateAuthorizationTokenResponse.Builder()
				                                                   .withCredential(credential)
				                                                   .build();
		Assert.assertNotNull(request);
		Assert.assertNotNull(request.getCredential());
	}

	@Test
	public void build_complete() {
		final ValidateAuthorizationTokenResponse request = new ValidateAuthorizationTokenResponse.Builder()
				                                                   .withCredential(credential)
				                                                   .build();
		Assert.assertNotNull(request);
		Assert.assertNotNull(request.getCredential());
	}
}
