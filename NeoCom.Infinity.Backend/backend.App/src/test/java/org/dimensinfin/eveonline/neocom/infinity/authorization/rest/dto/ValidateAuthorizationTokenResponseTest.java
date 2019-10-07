package org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import org.dimensinfin.eveonline.neocom.database.entities.Credential;

@RunWith(JUnit4.class)
public class ValidateAuthorizationTokenResponseTest {
	private Credential credential;

	@Before
	public void setUp() {
		this.credential = Mockito.mock( Credential.class );
	}

	@Test
	public void getterContract() {
		final ValidateAuthorizationTokenResponse request = new ValidateAuthorizationTokenResponse.Builder()
				.withCredential( credential )
				.withJwtToken( "FFJJWWTT-TTKKEENNFF" )
				.build();
		Assert.assertNotNull( request );
		Assert.assertNotNull( request.getCredential() );
		Assert.assertNotNull( request.getJwtToken() );
	}

	@Test
	public void buildComplete() {
		final ValidateAuthorizationTokenResponse request = new ValidateAuthorizationTokenResponse.Builder()
				.withCredential( credential )
				.withJwtToken( "FFJJWWTT-TTKKEENNFF" )
				.build();
		Assert.assertNotNull( request );
		Assert.assertNotNull( request.getCredential() );
	}

	@Test(expected = NullPointerException.class)
	public void buildiNComplete() {
		final ValidateAuthorizationTokenResponse request = new ValidateAuthorizationTokenResponse.Builder()
				.withCredential( null )
				.withJwtToken( "FFJJWWTT-TTKKEENNFF" )
				.build();
		Assert.assertNotNull( request );
		Assert.assertNotNull( request.getCredential() );
	}
}
