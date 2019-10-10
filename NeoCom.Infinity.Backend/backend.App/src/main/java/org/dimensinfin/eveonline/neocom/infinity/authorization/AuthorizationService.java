package org.dimensinfin.eveonline.neocom.infinity.authorization;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.joda.time.Instant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import org.dimensinfin.eveonline.neocom.adapters.ESIDataAdapter;
import org.dimensinfin.eveonline.neocom.auth.TokenRequestBody;
import org.dimensinfin.eveonline.neocom.auth.TokenTranslationResponse;
import org.dimensinfin.eveonline.neocom.auth.VerifyCharacterResponse;
import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.infinity.adapter.ConfigurationProviderWrapper;
import org.dimensinfin.eveonline.neocom.infinity.adapter.CredentialRepositoryWrapper;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.TokenVerification;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenRequest;
import org.dimensinfin.eveonline.neocom.infinity.authorization.rest.dto.ValidateAuthorizationTokenResponse;
import org.dimensinfin.eveonline.neocom.infinity.core.ErrorInfo;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComSBException;
import org.dimensinfin.eveonline.neocom.infinity.core.NeoComService;
import org.dimensinfin.eveonline.neocom.services.UpdaterJobManager;
import org.dimensinfin.eveonline.neocom.updaters.CredentialUpdater;

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
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.ISSUER;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.SECRET;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.SUBJECT;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.TOKEN_ACCOUNT_NAME_FIELD_NAME;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.TOKEN_CORPORATION_ID_FIELD_NAME;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.TOKEN_PILOT_ID_FIELD_NAME;
import static org.dimensinfin.eveonline.neocom.infinity.security.SecurityConstants.TOKEN_UNIQUE_IDENTIFIER_FIELD_NAME;

@Service
public class AuthorizationService extends NeoComService {
	public interface VerifyCharacter {
		@GET("/oauth/verify")
		Call<VerifyCharacterResponse> getVerification( @Header("Authorization") String token );
	}

	public interface GetAccessToken {
		@POST("/v2/oauth/token")
		Call<TokenTranslationResponse> getAccessToken( @Header("Authorization") String token,
		                                               @Header("Content-Type") String contentType,
		                                               @Header("Host") String host,
		                                               @Body TokenRequestBody body );
	}

	private final ConfigurationProviderWrapper configurationProvider;
	private final CredentialRepositoryWrapper credentialRepository;

	@Autowired
	public AuthorizationService( final ConfigurationProviderWrapper configurationProvider,
	                             final CredentialRepositoryWrapper credentialRepository/*,
	                            @Autowired final ESIDataAdapterComponent esiDataAdapter*/ ) {
		this.configurationProvider = configurationProvider;
		this.credentialRepository = credentialRepository;
//		this.esiDataAdapter = esiDataAdapter;
	}

	public ResponseEntity<ValidateAuthorizationTokenResponse> validateAuthorizationToken( final ValidateAuthorizationTokenRequest validateAuthorizationTokenRequest ) {
		logger.info( ">> [AuthorizationService.validateAuthorizationToken]" );
		final Instant timer = Instant.now();
		this.validateStateMatch( validateAuthorizationTokenRequest.getState() );

		// Create the authentication process data store
		String dataSource = ESIDataAdapter.DEFAULT_ESI_SERVER; // Set the default server identifier.
		if (validateAuthorizationTokenRequest.getDataSource().isPresent())
			dataSource = validateAuthorizationTokenRequest.getDataSource().get();
		final TokenVerification tokenVerificationStore = new TokenVerification()
				.setAuthCode( validateAuthorizationTokenRequest.getCode() )
				.setDataSource( dataSource.toLowerCase() );

		// Create the conversion call.
		logger.info( "-- [AuthorizationService.validateAuthorizationToken]> Preparing Verification HTTP request." );
		logger.info( "-- [AuthorizationService.validateAuthorizationToken]> Creating access token request." );
		tokenVerificationStore.setTokenTranslationResponse( this.getTokenTranslationResponse( tokenVerificationStore ) );
		// Create a security verification instance.
		tokenVerificationStore.setVerifyCharacterResponse( this.getVerifyCharacterResponse( tokenVerificationStore ) );

		// Create a new session and store the authorization token and the rest of the data.
		// Generate token.
//		tokenVerificationStore.setAccountIdentifier( tokenVerificationStore.getVerifyCharacterResponse().getCharacterID() );
//		final String salt = BCrypt.gensalt( 8 );
//		final String payload = DateTime.now().toString() + ":"
//				+ tokenVerificationStore.getAccountIdentifier() + ":"
//				+ tokenVerificationStore.getVerifyCharacterResponse().getCharacterName();
//		final String authorizationToken = BCrypt.hashpw( payload, salt );
//
//		// Build up the session along with the credential data.
//		this.buildSession( tokenVerificationStore );

		logger.info( "-- [AuthorizationService.validateAuthorizationToken]> Creating Credential..." );
		final TokenTranslationResponse token = tokenVerificationStore.getTokenTranslationResponse();
		final Credential credential = new Credential.Builder( tokenVerificationStore.getAccountIdentifier() )
				.withAccountId( tokenVerificationStore.getAccountIdentifier() )
				.withAccountName( tokenVerificationStore.getVerifyCharacterResponse().getCharacterName() )
				.withTokenType( token.getTokenType() )
				.withAccessToken( token.getAccessToken() )
				.withRefreshToken( token.getRefreshToken() )
				.withDataSource( tokenVerificationStore.getDataSource() )
				// TODO - This will require the ESI for a simple configurable data. Review this set. Probably scopes can be
				//  removed from Credentials
				.withScope( "publicData" )
				.build();
		try {
			this.credentialRepository.persist( credential );
			logger.info( "-- [AuthorizationService.validateAuthorizationToken]> Credential #{}-{} created successfully.",
					credential.getAccountId(), credential.getAccountName() );
			UpdaterJobManager.submit( new CredentialUpdater( credential ) ); // Post the update request to the scheduler.
			final String jwtToken = JWT.create()
					.withIssuer( ISSUER )
					.withSubject( SUBJECT )
					.withClaim( TOKEN_UNIQUE_IDENTIFIER_FIELD_NAME, credential.getUniqueId() )
					.withClaim( TOKEN_ACCOUNT_NAME_FIELD_NAME, credential.getAccountName() )
					.withClaim( TOKEN_CORPORATION_ID_FIELD_NAME, credential.getAccountId() )
					.withClaim( TOKEN_PILOT_ID_FIELD_NAME, credential.getAccountId() )
					.sign( Algorithm.HMAC512( SECRET ) );
			return new ResponseEntity(new ValidateAuthorizationTokenResponse.Builder()
					.withCredential( credential )
					.withJwtToken( jwtToken )
					.build(), HttpStatus.OK);
		} catch (final SQLException | UnsupportedEncodingException sqle) {
			logger.info( "-- [AuthorizationService.validateAuthorizationToken]> Response is {} - {}.",
					HttpStatus.BAD_REQUEST, sqle.getMessage() );
			throw new NeoComSBException( sqle );
		}
	}

	private boolean validateStateMatch( final String state ) {
		return true;
	}

	private TokenTranslationResponse getTokenTranslationResponse( final TokenVerification store ) {
		// Preload configuration variables.
		final String esiServer = store.getDataSource();
		final String authorizationServer = this.configurationProvider.getResourceString(
				"P.esi." + esiServer + ".authorization.server" );
		final String authorizationClientid = this.configurationProvider.getResourceString(
				"P.esi." + esiServer + ".authorization.clientid" );
		final String authorizationSecretKey = this.configurationProvider.getResourceString(
				"P.esi." + esiServer + ".authorization.secretkey" );
		final String authorizationContentType = this.configurationProvider.getResourceString(
				"P.esi.authorization.content.type" );

		final GetAccessToken serviceGetAccessToken = new Retrofit.Builder()
				.baseUrl( authorizationServer )
				.addConverterFactory( JacksonConverterFactory.create() )
				.build()
				.create( GetAccessToken.class );
		final TokenRequestBody tokenRequestBody = new TokenRequestBody().setCode( store.getAuthCode() );
		logger.info( "-- [AuthorizationService.getTokenTranslationResponse]> Creating request call." );
		final String peckString = authorizationClientid + ":" + authorizationSecretKey;
		String peck = Base64.getEncoder().encodeToString( peckString.getBytes() ).replaceAll( "\n", "" );
		store.setPeck( peck );
		final Call<TokenTranslationResponse> request = serviceGetAccessToken.getAccessToken(
				"Basic " + peck,authorizationContentType,
				"login.eveonline.com",
				tokenRequestBody
		);
		// Getting the request response to be stored if valid.
		try {
			final Response<TokenTranslationResponse> response = request.execute();
			if (response.isSuccessful()) {
				logger.info( "-- [AuthorizationService.getTokenTranslationResponse]> Response is 200 OK." );
				final TokenTranslationResponse token = response.body();
				return token;
			} else {
				logger.info( "-- [AuthorizationService.getTokenTranslationResponse]> Response is {} - {}.",
						HttpStatus.BAD_REQUEST, response.message() );
				throw new NeoComSBException( ErrorInfo.AUTHORIZATION_TRANSLATION );
			}
		} catch (IOException ioe) {
			logger.info( "-- [AuthorizationService.getTokenTranslationResponse]> Response is {} - {}.",
					HttpStatus.BAD_REQUEST, ioe.getMessage() );
			throw new NeoComSBException( ErrorInfo.AUTHORIZATION_TRANSLATION, ioe );
		}
	}

	private VerifyCharacterResponse getVerifyCharacterResponse( final TokenVerification store ) {
		OkHttpClient.Builder verifyClient =
				new OkHttpClient.Builder()
						.certificatePinner(
								new CertificatePinner.Builder()
										.add( "login.eveonline.com", "sha256/5UeWOuDyX7IUmcKnsVdx+vLMkxEGAtzfaOUQT/caUBE=" )
										.add( "login.eveonline.com", "sha256/980Ionqp3wkYtN9SZVgMzuWQzJta1nfxNPwTem1X0uc=" )
										.add( "login.eveonline.com", "sha256/du6FkDdMcVQ3u8prumAo6t3i3G27uMP2EOhR8R0at/U=" )
										.build() )
						.addInterceptor( chain -> chain.proceed(
								chain.request()
										.newBuilder()
										.addHeader( "User-Agent", "org.dimensinfin" )
										.build() ) );
		// Verify the character authenticated.
		logger.info( "-- [AuthorizationService.getVerifyCharacterResponse]> Creating character verification." );
		final VerifyCharacter verificationService = new Retrofit.Builder()
				.baseUrl( this.configurationProvider.getResourceString(
						"P.esi." +
								store.getDataSource().toLowerCase() +
								".authorization.server" ) )
				.addConverterFactory( JacksonConverterFactory.create() )
				.client( verifyClient.build() )
				.build()
				.create( VerifyCharacter.class );
		final String accessToken = store.getTokenTranslationResponse().getAccessToken();
		try {
			final Response<VerifyCharacterResponse> verificationResponse =
					verificationService.getVerification( "Bearer " + accessToken ).execute();
			if (verificationResponse.isSuccessful()) {
				logger.info( "-- [AuthorizationService.getVerifyCharacterResponse]> Character verification OK." );
				return verificationResponse.body();
			} else {
				logger.info( "-- [AuthorizationService.getVerifyCharacterResponse]> Response is {} - {}.",
						HttpStatus.BAD_REQUEST, verificationResponse.message() );
				throw new NeoComSBException( ErrorInfo.VERIFICATION_RESPONSE );

			}
		} catch (IOException ioe) {
			logger.info( "-- [AuthorizationService.getVerifyCharacterResponse]> Response is {} - {}.",
					HttpStatus.BAD_REQUEST, ioe.getMessage() );
			throw new NeoComSBException( ErrorInfo.VERIFICATION_RESPONSE, ioe );
		}
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
