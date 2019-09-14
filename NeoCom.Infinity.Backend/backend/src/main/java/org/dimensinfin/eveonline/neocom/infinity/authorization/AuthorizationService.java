package org.dimensinfin.eveonline.neocom.infinity.authorization;

import org.dimensinfin.eveonline.neocom.adapters.ESIDataAdapter;
import org.dimensinfin.eveonline.neocom.auth.TokenRequestBody;
import org.dimensinfin.eveonline.neocom.auth.TokenTranslationResponse;
import org.dimensinfin.eveonline.neocom.auth.VerifyCharacterResponse;
import org.dimensinfin.eveonline.neocom.core.updaters.CredentialUpdater;
import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.exception.NeoComException;
import org.dimensinfin.eveonline.neocom.infinity.adapter.ConfigurationProviderWrapper;
import org.dimensinfin.eveonline.neocom.infinity.adapter.CredentialRepositoryWrapper;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.TokenVerification;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComService;
import org.dimensinfin.eveonline.neocom.services.UpdaterJobManager;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Base64;

import jodd.util.BCrypt;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

@Service
public class AuthorizationService extends NeoComService {
	public interface VerifyCharacter {
		@GET("/oauth/verify")
		Call<VerifyCharacterResponse> getVerification( @Header("Authorization") String token );
	}

	public interface GetAccessToken {
		//		@POST("/persistPerson")
		@POST("/oauth/token")
		Call<TokenTranslationResponse> getAccessToken( @Header("Authorization") String token
				, @Header("Content-Type") String contentType
				, @Body TokenRequestBody body );
	}

	//	private final ESIDataAdapter esiDataAdapter;
	private final ConfigurationProviderWrapper configurationProvider;
	private final CredentialRepositoryWrapper credentialRepository;

	@Autowired
	public AuthorizationService(  final ConfigurationProviderWrapper configurationProvider,
	                              final CredentialRepositoryWrapper credentialRepository/*,
	                            @Autowired final ESIDataAdapterComponent esiDataAdapter*/ ) {
		this.configurationProvider = configurationProvider;
		this.credentialRepository = credentialRepository;
//		this.esiDataAdapter = esiDataAdapter;
	}

	public Credential validateAuthorizationToken( final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest
	) throws SQLException, NeoComException, IOException {
		logger.info(">> [AuthorizationService.validateAuthorizationToken]");
		final Instant timer = Instant.now();
		this.validateStateMatch(validateAuthorizationTokenRequest.getState());

		// Create the authentication process data store
		String dataSource = ESIDataAdapter.DEFAULT_ESI_SERVER; // Set the default server identifier.
		if (validateAuthorizationTokenRequest.getDataSource().isPresent())
			dataSource = validateAuthorizationTokenRequest.getDataSource().get();
		final TokenVerification tokenVerificationStore = new TokenVerification()
				                                                 .setAuthCode(validateAuthorizationTokenRequest.getCode())
				                                                 .setDataSource(dataSource);

		// Create the conversion call.
		logger.info("-- [AuthorizationService.validateAuthorizationToken]> Preparing Verification HTTP request.");
		logger.info("-- [AuthorizationService.validateAuthorizationToken]> Creating access token request.");
		tokenVerificationStore.setTokenTranslationResponse(this.getTokenTranslationResponse(tokenVerificationStore));
		// Create a security verification instance.
		tokenVerificationStore.setVerifyCharacterResponse(this.getVerifyCharacterResponse(tokenVerificationStore));
		logger.info("-- [AuthorizationService.validateAuthorizationToken]> Character verification OK.");

		// Create a new session and store the authorization token and the rest of the data.
		// Generate token.
		tokenVerificationStore.setAccountIdentifier(tokenVerificationStore.getVerifyCharacterResponse().getCharacterID());
		final String salt = BCrypt.gensalt(8);
		final String payload = DateTime.now().toString() + ":"
				                       + tokenVerificationStore.getAccountIdentifier() + ":"
				                       + tokenVerificationStore.getVerifyCharacterResponse().getCharacterName();
		final String authorizationToken = BCrypt.hashpw(payload, salt);

		// Build up the session along with the credential data.
		this.buildSession(tokenVerificationStore);

		final TokenTranslationResponse token = tokenVerificationStore.getTokenTranslationResponse();
		// TODO - replace this by the new creation pattern.
		final Credential credential = new Credential.Builder(tokenVerificationStore.getAccountIdentifier())
				                              .withAccountId(tokenVerificationStore.getAccountIdentifier())
				                              .withAccountName(
						                              tokenVerificationStore.getVerifyCharacterResponse().getCharacterName())
				                              .withTokenType(token.getTokenType())
				                              .withAccessToken(token.getAccessToken())
				                              .withRefreshToken(token.getRefreshToken())
				                              .withDataSource(tokenVerificationStore.getDataSource())
				                              // TODO - This will require the ESI for a simple configurable data. Review this set.
				                              .withScope("publicData")
				                              .build();
		// Update the additional render fields.
		UpdaterJobManager.submit(new CredentialUpdater(credential)); // Post the update request to the scheduler.
		this.credentialRepository.persist(credential);
		logger.info("-- [AuthorizationFlowActivity.registerInBackground]> Credential #{}-{} created successfully.",
		            credential.getAccountId(), credential.getAccountName());
		return credential;
	}

	private boolean validateStateMatch( final String state ) {
		return true;
	}

	private TokenTranslationResponse getTokenTranslationResponse( final TokenVerification store ) throws NeoComException, IOException {
		final GetAccessToken serviceGetAccessToken = new Retrofit.Builder()
				                                             .baseUrl(this.configurationProvider.getResourceString(
						                                             "P.esi.authorization.authorizationserver"))
				                                             .addConverterFactory(JacksonConverterFactory.create())
				                                             .build()
				                                             .create(GetAccessToken.class);
		logger.info("-- [AuthorizationService.getTokenTranslationResponse]> Creating request body.");
		final TokenRequestBody tokenRequestBody = new TokenRequestBody().setCode(store.getAuthCode());
		logger.info("-- [AuthorizationService.getTokenTranslationResponse]> Creating request call.");
		final String peckString = this.configurationProvider.getResourceString("P.esi.authorization.clientid") +
				                          ":" +
				                          this.configurationProvider.getResourceString("P.esi.authorization.secretkey");
		final String peck = Base64.getEncoder().encodeToString(peckString.getBytes());
		store.setPeck(peck);
		final Call<TokenTranslationResponse> request = serviceGetAccessToken.getAccessToken("Basic " + peck,
		                                                                                    GlobalDataManager.getResourceString(
				                                                                                    "P.esi.authorization.contenttype"),
		                                                                                    tokenRequestBody);
		// Getting the request response to be stored if valid.
		final Response<TokenTranslationResponse> response = request.execute();
		if (response.isSuccessful()) {
			logger.info("-- [AuthorizationService.getTokenTranslationResponse]> Response is 200 OK.");
			final TokenTranslationResponse token = response.body();
			return token;
		} else
			throw new NeoComException(
					"NEO [AuthorizationService.getTokenTranslationResponse]> the TokenTranslationResponse response is" +
							" invalid. "
							+ response.message());
	}

	private VerifyCharacterResponse getVerifyCharacterResponse( final TokenVerification store ) throws NeoComException, IOException {
		OkHttpClient.Builder verifyClient =
				new OkHttpClient.Builder()
						.certificatePinner(
								new CertificatePinner.Builder()
										.add("login.eveonline.com", "sha256/5UeWOuDyX7IUmcKnsVdx+vLMkxEGAtzfaOUQT/caUBE=")
										.add("login.eveonline.com", "sha256/980Ionqp3wkYtN9SZVgMzuWQzJta1nfxNPwTem1X0uc=")
										.add("login.eveonline.com", "sha256/du6FkDdMcVQ3u8prumAo6t3i3G27uMP2EOhR8R0at/U=")
										.build())
						.addInterceptor(chain -> chain.proceed(
								chain.request()
										.newBuilder()
										.addHeader("User-Agent", "org.dimensinfin")
										.build()));
		// Verify the character authenticated.
		logger.info("-- [AuthorizationService.getVerifyCharacterResponse]> Creating character verification.");
		final VerifyCharacter verificationService = new Retrofit.Builder()
				                                            .baseUrl(this.configurationProvider.getResourceString(
						                                            "P.esi.authorization.authorizationserver"))
				                                            .addConverterFactory(JacksonConverterFactory.create())
				                                            .client(verifyClient.build())
				                                            .build()
				                                            .create(VerifyCharacter.class);
		final String accessToken = store.getTokenTranslationResponse().getAccessToken();
		final Response<VerifyCharacterResponse> verificationResponse = verificationService.getVerification(
				"Bearer " + accessToken)
				                                                               .execute();
		if (verificationResponse.isSuccessful()) {
			logger.info("-- [AuthorizationService.getVerifyCharacterResponse]> Character verification OK.");
			return verificationResponse.body();
		} else
			throw new NeoComException(
					"NEO [AuthorizationService.getVerifyCharacterResponse]> the VerifyCharacterResponse response is " +
							"invalid. " +
							verificationResponse.message());
	}

	private void buildSession( final TokenVerification store ) {
		//  TODO - Use this to register the session
		//		final SessionManager.AppSession session = new SessionManager.AppSession()
//				.setAuthorizationToken(authorizationToken)
//				.setPayload(payload)
//				.setAuthorizationPassword(salt)
//				.setUserIdentifier(newAccountIdentifier)
//				.setUserName(verificationResponse.body().getCharacterName());
////							.setCredential ( credential )
////							.setRole(credencial.getRole())
////							.store();

		// TODO - This has to be replaced by the new session management.
//	final SessionManager.AppSession session = new SessionManager.AppSession()
//			.setAuthorizationToken(authorizationToken)
//			.setPayload(payload)
//			.setAuthorizationPassword(salt)
//			.setUserIdentifier(newAccountIdentifier)
//			.setUserName(verificationResponse.body().getCharacterName());
////							.setCredential ( credential )
////							.setRole(credencial.getRole())
////							.store();
//	logger.info("-- [LoginController.checkCredencial]> Session id: {}", session.getId());
	}
}
