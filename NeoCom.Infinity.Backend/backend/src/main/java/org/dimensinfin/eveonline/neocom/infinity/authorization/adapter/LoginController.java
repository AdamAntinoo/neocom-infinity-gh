package org.dimensinfin.eveonline.neocom.infinity.authorization.adapter;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import jodd.util.BCrypt;
import org.dimensinfin.eveonline.neocom.auth.*;
import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.infinity.adapters.ConfigurationProviderComponent;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.dimensinfin.core.util.Chrono;
import org.dimensinfin.eveonline.neocom.NeoComMicroServiceApplication;
import org.dimensinfin.eveonline.neocom.datamngmt.ESINetworkManager;
import org.dimensinfin.eveonline.neocom.exception.NeoComException;
import org.dimensinfin.eveonline.neocom.entities.Colony;
import org.dimensinfin.eveonline.neocom.entities.Credential;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.datamngmt.InfinityGlobalDataManager;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.model.PilotV2;
import org.dimensinfin.eveonline.neocom.security.SessionManager;

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

@RestController
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	private ConfigurationProviderComponent configurationProvider;

	public LoginController(@Autowired final ConfigurationProviderComponent configurationProvider) {
		this.configurationProvider = configurationProvider;
	}

	@CrossOrigin()
	@RequestMapping(path = {"/api/v1/validateAuthorizationToken/{code}/state/{state}",
			"/api/v1/validateAuthorizationToken/{code}/state/{state}/datasource/{dataSource}"}
			, method = RequestMethod.GET,
			consumes = "application/json",
			produces = "application/json")
	public ResponseEntity<LoginResponse> validateAuthorizationToken(@PathVariable final String code,
	                                                                @PathVariable final String state,
	                                                                @PathVariable("dataSource") final Optional<String> dataSource
	) throws NeoComException, IOException {
		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/validateAuthorizationToken/{}/state/{state}", code, state);
		logger.info(">> [LoginController.validateAuthorizationToken]");
		final Instant timer = Instant.now();
		this.validateStateMatch(state,dataSource);
		// With the 'code' complete the authorization flow and generate a new session.
		return new ResponseEntity<LoginResponse>(this.validateAuthorization(code), HttpStatus.OK);
	}

	private boolean validateStateMatch(final String state) {
		return true;
	}

	private LoginResponse validateAuthorization(final String authCode,final String dataSource)
			throws NeoComException, IOException {
		// Create the conversion call by coding.
		logger.info("-- [LoginController.validateAuthorization]> Preparing Verification HTTP request.");
		final TokenVerification tokenVerificationStore = new TokenVerification()
				.setAuthCode(authCode)
				.setDataSource(dataSource);

		logger.info("-- [LoginController.validateAuthorization]> Creating access token request.");
		tokenVerificationStore.setTokenTranslationResponse(this.getTokenTranslationResponse(tokenVerificationStore));

//		try {
		// Create a Retrofit request service to encapsulate the call.
//		final GetAccessToken serviceGetAccessToken = new Retrofit.Builder()
//				.baseUrl(GlobalDataManager.getResourceString("R.esi.authorization.authorizationserver"))
//				.addConverterFactory(JacksonConverterFactory.create())
//				.build()
//				.create(GetAccessToken.class);
//		logger.info("-- [LoginController.exchangeAuthorization]> Creating request body.");
//		final TokenRequestBody tokenRequestBody = new TokenRequestBody()
//				.setCode(authCode);
//		logger.info("-- [LoginController.exchangeAuthorization]> Creating request call.");
//		final String peckString = GlobalDataManager.getResourceString("R.esi.authorization.clientid")
//				+ ":"
//				+ GlobalDataManager.getResourceString("R.esi.authorization.secretkey");
//		final String peck = Base64.getEncoder().encodeToString(peckString.getBytes());
//		final Call<TokenTranslationResponse> request = serviceGetAccessToken.getAccessToken("Basic " + peck
//				, GlobalDataManager.getResourceString("R.esi.authorization.contenttype")
//				, tokenRequestBody);
//		// Getting the request response to be stored if valid.
//		final Response<TokenTranslationResponse> response = request.execute();
//		if (response.isSuccessful()) {
//			logger.info("-- [LoginController.exchangeAuthorization]> Response is 200 OK.");
//			final TokenTranslationResponse token = response.body();
		// Create a security verification instance.
		tokenVerificationStore.setVerifyCharacterResponse(this.getVerifyCharacterResponse(tokenVerificationStore));

//		OkHttpClient.Builder verifyClient =
//				new OkHttpClient.Builder()
//						.certificatePinner(
//								new CertificatePinner.Builder()
//										.add("login.eveonline.com", "sha256/5UeWOuDyX7IUmcKnsVdx+vLMkxEGAtzfaOUQT/caUBE=")
//										.add("login.eveonline.com", "sha256/980Ionqp3wkYtN9SZVgMzuWQzJta1nfxNPwTem1X0uc=")
//										.add("login.eveonline.com", "sha256/du6FkDdMcVQ3u8prumAo6t3i3G27uMP2EOhR8R0at/U=")
//										.build())
//						.addInterceptor(chain -> chain.proceed(
//								chain.request()
//										.newBuilder()
//										.addHeader("User-Agent", "org.dimensinfin")
//										.build()));
//		// Verify the character authenticated and create the Credential.
//		logger.info("-- [LoginController.validateAuthorization]> Creating character verification.");
//		final VerifyCharacter verificationService = new Retrofit.Builder()
//				.baseUrl(GlobalDataManager.getResourceString("P.esi.authorization.authorizationserver"))
//				.addConverterFactory(JacksonConverterFactory.create())
//				.client(verifyClient.build())
//				.build()
//				.create(VerifyCharacter.class);
//		final String accessToken = token.getAccessToken();
//		final Response<VerifyCharacterResponse> verificationResponse = verificationService.getVerification("Bearer " + accessToken).execute();
//		if (verificationResponse.isSuccessful()) {
		logger.info("-- [LoginController.exchangeAuthorization]> Character verification OK.");

//					logger.info("-- [LoginController.checkCredencial]> Password matches.");
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
//		final SessionManager.AppSession session = new SessionManager.AppSession()
//				.setAuthorizationToken(authorizationToken)
//				.setPayload(payload)
//				.setAuthorizationPassword(salt)
//				.setUserIdentifier(newAccountIdentifier)
//				.setUserName(verificationResponse.body().getCharacterName());
////							.setCredential ( credential )
////							.setRole(credencial.getRole())
////							.store();
//		logger.info("-- [LoginController.checkCredencial]> Session id: {}", session.getId());

		// Construct the login response that it is a structure with the token and the Pilot credential.
		final TokenTranslationResponse token = tokenVerificationStore.getTokenTranslationResponse();
		// TODO - replace this by the new creation pattern.
		final Credential credential = new Credential(tokenVerificationStore.getAccountIdentifier())
				.setAccountName(tokenVerificationStore.getVerifyCharacterResponse().getCharacterName())
				.setAccessToken(token.getAccessToken())
				.setTokenType(token.getTokenType())
//				.setExpires(Instant.now().plus(TimeUnit.SECONDS.toMillis(token.getExpires())).getMillis())
				.setRefreshToken(token.getRefreshToken())
				.setDataSource(tokenVerificationStore.getDataSource())
//				.setScope(ESINetworkManager.getStringScopes())
				.store();
		session.setCredential(credential)
				.store();

		final PilotV2 pilotData = InfinityGlobalDataManager.requestPilotV2(credential);
//					session.setPilotPublicData(pilotData);
		// Create the response with the session token to conenct back the session on next calls.
		final LoginResponse loginResponse = new LoginResponse()
				.setAuthorizationToken(authorizationToken)
				.setPilotPublicData(pilotData);
		return loginResponse;


		// TODO Create a new session store to keep the session data.
		// Create the credential and store it on a new encrypted session.
//					NeoComMicroServiceApplication.sessionStore.put(session.getSessionId(), session);
//					// TODO - Store an additional copy of the session under a predefined session identifier.
//					NeoComMicroServiceApplication.sessionStore.put("-MOCK-SESSION-LOCATOR-", session);

		// Clean up the list of credential to force a reload on next access.
		// TODO This is a new code flow to store credentials only on the session and this on the database.

//					DataManagementModelStore.getSingleton().cleanModel();
//					// Update the Pilot information.
//					InfinityGlobalDataManager.requestPilotV2(credential);
//					return session;
//					return new NeoComMicroServiceApplication.NeoComSessionIdentifier(session);
//		} else
//			throw new NeoComException("NEO [LoginController.exchangeAuthorization]> the VerifyCharacterResponse response is " +
//					"invalid. "
//					+ verificationResponse.message());
//		} else
//			throw new NeoComException("NEO [LoginController.exchangeAuthorization]> the TokenTranslationResponse response is " +
//					"invalid. "
//					+ response.message());
//		} catch (IOException ioe) {
//			logger.error("ER [LoginController.exchangeAuthorization]> IO Exception on authorization request call. " + ioe
//					.getMessage());
//			return exceptionSerialization(ioe);
//		} catch (NoSuchAlgorithmException nsae) {
//			logger.error("ER [LoginController.exchangeAuthorization]> IO Exception on authorization request call. " + nsae
//					.getMessage());
//			return exceptionSerialization(nsae);
//		} catch (Exception ge) {
//			logger.error("ER [LoginController.exchangeAuthorization]> IO Exception on authorization request call. " + ge
//					.getMessage());
//			return exceptionSerialization(ge);
//		} finally {
//			// All went perfect. signal the end of the process
//			logger.info("<< [LoginController.exchangeAuthorization]");
//		}
	}

	private TokenTranslationResponse getTokenTranslationResponse(final TokenVerification store) throws NeoComException, IOException {
		final GetAccessToken serviceGetAccessToken = new Retrofit.Builder()
				.baseUrl(this.configurationProvider.getResourceString("P.esi.authorization.authorizationserver"))
				.addConverterFactory(JacksonConverterFactory.create())
				.build()
				.create(GetAccessToken.class);
		logger.info("-- [LoginController.getTokenTranslationResponse]> Creating request body.");
		final TokenRequestBody tokenRequestBody = new TokenRequestBody().setCode(store.getAuthCode());
		logger.info("-- [LoginController.getTokenTranslationResponse]> Creating request call.");
		final String peckString = this.configurationProvider.getResourceString("P.esi.authorization.clientid") +
				":" +
				this.configurationProvider.getResourceString("P.esi.authorization.secretkey");
		final String peck = Base64.getEncoder().encodeToString(peckString.getBytes());
		store.setPeck(peck);
		final Call<TokenTranslationResponse> request = serviceGetAccessToken.getAccessToken("Basic " + peck,
				GlobalDataManager.getResourceString("P.esi.authorization.contenttype"),
				tokenRequestBody);
		// Getting the request response to be stored if valid.
		final Response<TokenTranslationResponse> response = request.execute();
		if (response.isSuccessful()) {
			logger.info("-- [LoginController.getTokenTranslationResponse]> Response is 200 OK.");
			final TokenTranslationResponse token = response.body();
			return token;
		} else throw new NeoComException("NEO [LoginController.exchangeAuthorization]> the TokenTranslationResponse response is" +
				" invalid. "
				+ response.message());
	}

	private VerifyCharacterResponse getVerifyCharacterResponse(final TokenVerification store) throws NeoComException, IOException {
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
		logger.info("-- [LoginController.getVerifyCharacterResponse]> Creating character verification.");
		final VerifyCharacter verificationService = new Retrofit.Builder()
				.baseUrl(this.configurationProvider.getResourceString("P.esi.authorization.authorizationserver"))
				.addConverterFactory(JacksonConverterFactory.create())
				.client(verifyClient.build())
				.build()
				.create(VerifyCharacter.class);
		final String accessToken = store.getTokenTranslationResponse().getAccessToken();
		final Response<VerifyCharacterResponse> verificationResponse = verificationService.getVerification("Bearer " + accessToken)
				.execute();
		if (verificationResponse.isSuccessful()) {
			logger.info("-- [LoginController.getVerifyCharacterResponse]> Character verification OK.");
			return verificationResponse.body();
		} else
			throw new NeoComException("NEO [LoginController.getVerifyCharacterResponse]> the VerifyCharacterResponse response is " +
					"invalid. " +
					verificationResponse.message());
	}

	private void buildSession(final TokenVerification store) {
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

	private String exceptionSerialization(final Exception exc) {
		String serializedException = null;
		try {
			serializedException = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(exc);
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			serializedException = new JsonExceptionInstance(jpe.getMessage()).toJson();
		}
		return serializedException;
	}

	private String serializeCredentialList(final List<Credential> credentials) {
		// Use my own serialization control to return the data to generate exactly what I want.
		String contentsSerialized = "[jsonClass: \"Exception\"," +
				"message: \"Unprocessed data. Possible JsonProcessingException exception.\"]";
		try {
			contentsSerialized = NeoComMicroServiceApplication.jsonMapper.writeValueAsString(credentials);
		} catch (JsonProcessingException jpe) {
			jpe.printStackTrace();
			contentsSerialized = new JsonExceptionInstance(jpe.getMessage()).toJson();
		}
		return contentsSerialized;
	}

	public interface VerifyCharacter {
		@GET("/oauth/verify")
		Call<VerifyCharacterResponse> getVerification(@Header("Authorization") String token);
	}

	public interface GetAccessToken {
		//		@POST("/persistPerson")
		@POST("/oauth/token")
		Call<TokenTranslationResponse> getAccessToken(@Header("Authorization") String token
				, @Header("Content-Type") String contentType
				, @Body TokenRequestBody body);
	}

	public static class LoginResponse {
		private String authorizationToken;
		private PilotV2 pilotPublicData;

		public String getAuthorizationToken() {
			return authorizationToken;
		}

		public LoginResponse setAuthorizationToken(final String authorizationToken) {
			this.authorizationToken = authorizationToken;
			return this;
		}

		public PilotV2 getPilotPublicData() {
			return pilotPublicData;
		}

		public LoginResponse setPilotPublicData(final PilotV2 pilotPublicData) {
			this.pilotPublicData = pilotPublicData;
			return this;
		}
	}

	@ExceptionHandler({NeoComException.class})
	public ResponseEntity<LoginResponse> handleNeoComException(final NeoComException neoe) {
		// Convert the exception to a json response
		HttpHeaders headers = new HttpHeaders();
		headers.add("xApp-Error-Message", neoe.getMessage());
		logger.info("EX [LoginController.exchangeAuthorizationEntryPoint]> Exception: {}", neoe.getMessage());
		return new ResponseEntity<LoginResponse>(null, headers, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({IOException.class})
	public ResponseEntity<LoginResponse> handleIOException(final IOException ioe) {
		return new ResponseEntity<LoginResponse>(HttpStatus.UNAUTHORIZED);
	}
}
