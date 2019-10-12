package org.dimensinfin.eveonline.neocom.infinity.authorization.rest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import org.dimensinfin.eveonline.neocom.auth.TokenTranslationResponse;
import org.dimensinfin.eveonline.neocom.auth.VerifyCharacterResponse;
import org.dimensinfin.eveonline.neocom.infinity.authorization.support.TokenVerification;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComSBException;

public class TokenVerificationTest {
	private TokenTranslationResponse tokenTranslationResponse;
	private VerifyCharacterResponse verifyCharacterResponse;
	private TokenVerification tokenVerification;

	@Before
	public void setUp() throws Exception {
		this.tokenTranslationResponse = Mockito.mock( TokenTranslationResponse.class );
		this.verifyCharacterResponse = Mockito.mock( VerifyCharacterResponse.class );
		Mockito.when( this.verifyCharacterResponse.getCharacterID() )
				.thenReturn( 123456L );
		this.tokenVerification = new TokenVerification()
				.setAuthCode( "-TEST-AUTH-CODE-" )
				.setDataSource( "Tranquility" )
				.setTokenTranslationResponse( this.tokenTranslationResponse )
				.setVerifyCharacterResponse( this.verifyCharacterResponse )
				.setPeck( "-PECK-" );
	}

	@Test
	public void getterContract() {
		Assert.assertEquals( "-TEST-AUTH-CODE-", this.tokenVerification.getAuthCode() );
		Assert.assertEquals( "Tranquility", this.tokenVerification.getDataSource() );
		Assert.assertEquals( this.tokenTranslationResponse, this.tokenVerification.getTokenTranslationResponse() );
		Assert.assertEquals( "-PECK-", this.tokenVerification.getPeck() );
		Assert.assertEquals( 123456, this.tokenVerification.getAccountIdentifier() );
		Assert.assertEquals( this.verifyCharacterResponse, this.tokenVerification.getVerifyCharacterResponse() );
	}

	@Test
	public void getAccountIdentifierValid() {
		final Long identifier = Long.valueOf( 321654 );
		Mockito.when( this.verifyCharacterResponse.getCharacterID() )
				.thenReturn( identifier );
		Assert.assertEquals( identifier.longValue(), this.tokenVerification.getAccountIdentifier() );
	}

	@Test(expected = NeoComSBException.class)
	public void getAccountIdentifierFailure() {
		this.tokenVerification.setVerifyCharacterResponse( null );
		this.tokenVerification.getAccountIdentifier();
	}
}