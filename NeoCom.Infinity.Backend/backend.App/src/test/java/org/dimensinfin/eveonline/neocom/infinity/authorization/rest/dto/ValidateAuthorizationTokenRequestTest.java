package org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto;

import org.junit.Assert;
import org.junit.Test;

public class ValidateAuthorizationTokenRequestTest {
	@Test
	public void getterContract() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				.withCode( "-TEST-CODE-" )
				.withState( "-TEST-STATE-" )
				.optionalDataSource( "-OPTIONAL-DATA-SOURCE-" )
				.build();
		Assert.assertNotNull( request );
		Assert.assertTrue( request.getDataSource().isPresent() );
		Assert.assertEquals( "-TEST-CODE-", request.getCode() );
		Assert.assertEquals( "-TEST-STATE-", request.getState() );
		Assert.assertEquals( "-OPTIONAL-DATA-SOURCE-", request.getDataSource().get() );
		Assert.assertEquals( "-OPTIONAL-DATA-SOURCE-", request.getDataSourceName() );
	}

	@Test
	public void buildIncomplete() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				.withCode( "-TEST-CODE-" )
				.withState( "-TEST-STATE-" )
				.build();
		Assert.assertNotNull( request );
		Assert.assertFalse( request.getDataSource().isPresent() );
	}

	@Test(expected = NullPointerException.class)
	public void buildNullWith() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				.withCode( "-TEST-CODE-" )
				.withState( null )
				.optionalDataSource( null )
				.build();
		Assert.assertNotNull( request );
		Assert.assertFalse( request.getDataSource().isPresent() );
	}

	@Test
	public void buildNullOptional() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				.withCode( "-TEST-CODE-" )
				.withState( "-TEST-STATE-" )
				.optionalDataSource( null )
				.build();
		Assert.assertNotNull( request );
		Assert.assertFalse( request.getDataSource().isPresent() );
	}

	@Test
	public void buildComplete() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				.withCode( "-TEST-CODE-" )
				.withState( "-TEST-STATE-" )
				.optionalDataSource( "-OPTIONAL-DATA-SOURCE-" )
				.build();
		Assert.assertNotNull( request );
		Assert.assertTrue( request.getDataSource().isPresent() );
		Assert.assertEquals( "-OPTIONAL-DATA-SOURCE-", request.getDataSource().get() );
	}
}
