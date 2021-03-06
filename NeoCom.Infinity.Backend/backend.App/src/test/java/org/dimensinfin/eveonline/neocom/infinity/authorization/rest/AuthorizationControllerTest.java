package org.dimensinfin.eveonline.neocom.infinity.authorization.rest;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.eveonline.neocom.infinity.authorization.AuthorizationController;
import org.dimensinfin.eveonline.neocom.infinity.authorization.AuthorizationService;
import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.client.v1.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.core.exceptions.NeoComSBException;

import static org.dimensinfin.eveonline.neocom.infinity.core.exceptions.ErrorInfo.AUTHORIZATION_TRANSLATION;

public class AuthorizationControllerTest {
	private AuthorizationService authorizationService;

	private AuthorizationController authorizationController;

	@Before
	public void setUp() throws Exception {
		this.authorizationService = Mockito.mock( AuthorizationService.class );
		this.authorizationController = new AuthorizationController( this.authorizationService );
	}

	@Test
	public void validateAllParametersValid() {
		// Given
		final String code = "-CODE-";
		final String state = "-STATE-";
		final Optional<String> dataSource = Optional.of( "-DATASOURCE-" );
		ValidateAuthorizationTokenRequest request = Mockito.mock( ValidateAuthorizationTokenRequest.class );
		ValidateAuthorizationTokenResponse response = Mockito.mock( ValidateAuthorizationTokenResponse.class );
		final ResponseEntity<ValidateAuthorizationTokenResponse> responseEntity = new ResponseEntity( response, HttpStatus.OK );
		//When
		Mockito.when( this.authorizationService
				.validateAuthorizationToken( Mockito.any( ValidateAuthorizationTokenRequest.class ) ) )
				.thenReturn( responseEntity );
		// Test
		ResponseEntity<ValidateAuthorizationTokenResponse> obtainedEntity =
				this.authorizationController.validate( code, state, dataSource );
		// Asserts
		Assert.assertEquals( HttpStatus.OK, obtainedEntity.getStatusCode() );
		Assert.assertNotNull( obtainedEntity.getBody() );
	}

	@Test
	public void validateNotOptionalValid() {
		// Given
		final String code = "-CODE-";
		final String state = "-STATE-";
		final Optional<String> dataSource = Optional.empty();
		ValidateAuthorizationTokenRequest request = Mockito.mock( ValidateAuthorizationTokenRequest.class );
		ValidateAuthorizationTokenResponse response = Mockito.mock( ValidateAuthorizationTokenResponse.class );
		final ResponseEntity<ValidateAuthorizationTokenResponse> responseEntity = new ResponseEntity( response, HttpStatus.OK );
		//When
		Mockito.when( this.authorizationService
				.validateAuthorizationToken( Mockito.any( ValidateAuthorizationTokenRequest.class ) ) )
				.thenReturn( responseEntity );
		// Test
		ResponseEntity<ValidateAuthorizationTokenResponse> obtainedEntity =
				this.authorizationController.validate( code, state, dataSource );
		// Asserts
		Assert.assertEquals( HttpStatus.OK, obtainedEntity.getStatusCode() );
		Assert.assertNotNull( obtainedEntity.getBody() );
	}

	@Test(expected = NeoComSBException.class)
	public void validateFailure() {
		// Given
		final String code = "-CODE-";
		final String state = "-STATE-";
		final Optional<String> dataSource = Optional.empty();
		ValidateAuthorizationTokenRequest request = Mockito.mock( ValidateAuthorizationTokenRequest.class );
		ValidateAuthorizationTokenResponse response = Mockito.mock( ValidateAuthorizationTokenResponse.class );
		final ResponseEntity<ValidateAuthorizationTokenResponse> responseEntity = new ResponseEntity( response, HttpStatus.OK );
		//When
		Mockito.when( this.authorizationService
				.validateAuthorizationToken( Mockito.any( ValidateAuthorizationTokenRequest.class ) ) )
				.thenThrow( new NeoComSBException( AUTHORIZATION_TRANSLATION ) );
		// Test
		ResponseEntity<ValidateAuthorizationTokenResponse> obtainedEntity =
				this.authorizationController.validate( code, state, dataSource );
	}
}