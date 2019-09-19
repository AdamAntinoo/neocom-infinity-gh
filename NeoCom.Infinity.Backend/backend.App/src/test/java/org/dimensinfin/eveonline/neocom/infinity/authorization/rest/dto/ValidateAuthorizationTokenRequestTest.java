package org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto;

import org.junit.Assert;
import org.junit.Test;
public class ValidateAuthorizationTokenRequestTest {
	@Test
	public void assertContract() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				                                                  .withCode("-TEST-CODE-")
				                                                  .withState("-TEST-STATE-")
				                                                  .optionalDataSource("-OPTIONAL-DATA-SOURCE-")
				                                                  .build();
		Assert.assertNotNull(request);
		Assert.assertTrue(request.getDataSource().isPresent());
		Assert.assertEquals("-TEST-CODE-", request.getCode());
		Assert.assertEquals("-TEST-STATE-", request.getState());
		Assert.assertEquals("-OPTIONAL-DATA-SOURCE-", request.getDataSource().get());
	}

	@Test
	public void build_incomplete() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				                                                  .withCode("-TEST-CODE-")
				                                                  .withState("-TEST-STATE-")
				                                                  .build();
		Assert.assertNotNull(request);
		Assert.assertFalse(request.getDataSource().isPresent());
	}
	@Test(expected = NullPointerException.class)
	public void build_nullWith() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				                                                  .withCode("-TEST-CODE-")
				                                                  .withState(null)
				                                                  .optionalDataSource(null)
				                                                  .build();
		Assert.assertNotNull(request);
		Assert.assertFalse(request.getDataSource().isPresent());
	}
	@Test
	public void build_nullOptional() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				                                                  .withCode("-TEST-CODE-")
				                                                  .withState("-TEST-STATE-")
				                                                  .optionalDataSource(null)
				                                                  .build();
		Assert.assertNotNull(request);
		Assert.assertFalse(request.getDataSource().isPresent());
	}

	@Test
	public void build_complete() {
		final ValidateAuthorizationTokenRequest request = new ValidateAuthorizationTokenRequest.Builder()
				                                                  .withCode("-TEST-CODE-")
				                                                  .withState("-TEST-STATE-")
				                                                  .optionalDataSource("-OPTIONAL-DATA-SOURCE-")
				                                                  .build();
		Assert.assertNotNull(request);
		Assert.assertTrue(request.getDataSource().isPresent());
		Assert.assertEquals("-OPTIONAL-DATA-SOURCE-", request.getDataSource().get());
	}
}
