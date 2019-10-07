package org.dimensinfin.eveonline.neocom.infinity.authorization;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import org.dimensinfin.eveonline.neocom.infinity.adapter.CredentialRepositoryWrapper;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.support.SupportConfigurationProviderWrapper;

//@RunWith(MockitoJUnitRunner.class)
public class AuthorizationServiceTest {
	private SupportConfigurationProviderWrapper configurationProvider;
	private CredentialRepositoryWrapper credentialRepository;
//	@InjectMocks
	private AuthorizationService authorizationService;

	@Before
	public void setUp() throws Exception {
		this.configurationProvider = new SupportConfigurationProviderWrapper("default");
		this.credentialRepository = Mockito.mock( CredentialRepositoryWrapper.class );
		this.authorizationService = new AuthorizationService( this.configurationProvider,
				this.credentialRepository);
	}

	@Test
	public void validateAuthorizationToken() {
		final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest =
				new ValidateAuthorizationTokenRequest.Builder()
						.withCode( "-VALID-CODE-" )
						.withState( "-ANY-STATE-IS-VALID-" )
						.build();
		final ResponseEntity<ValidateAuthorizationTokenResponse> obtainedResponse = this.authorizationService
				.validateAuthorizationToken( validateAuthorizationTokenRequest );

		Assert.assertNotNull( obtainedResponse );
	}
}