package org.dimensinfin.eveonline.neocom.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import org.dimensinfin.eveonline.neocom.NeoComComponentFactory;
import org.dimensinfin.eveonline.neocom.auth.TokenRequestBody;
import org.dimensinfin.eveonline.neocom.auth.TokenTranslationResponse;
import org.dimensinfin.eveonline.neocom.auth.VerifyCharacterResponse;
import org.dimensinfin.eveonline.neocom.database.entities.Credential;
import org.dimensinfin.eveonline.neocom.datamngmt.GlobalDataManager;
import org.dimensinfin.eveonline.neocom.domain.PilotV2;
import org.dimensinfin.eveonline.neocom.exception.JsonExceptionInstance;
import org.dimensinfin.eveonline.neocom.exception.NeoComException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@RestController
public class LoginController {
    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    private ObjectMapper jsonMapper;

    public LoginController(@Autowired final NeoComComponentFactory componentFactory) {
        this.jsonMapper = componentFactory.getJsonMapper();
    }

    // - F I E L D - S E C T I O N ............................................................................

    // - C O N S T R U C T O R - S E C T I O N ................................................................

    // - M E T H O D - S E C T I O N ..........................................................................
    // TODO Code commented out until reactivation of functionality required.
//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/loginlist", method = RequestMethod.GET, produces = "application/json")
//	public Hashtable<String, Login> loginlistEntryPoint (
//			@RequestParam(value = "force", required = false) final String force) {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/loginlist");
//		logger.info(">> [LoginController.loginlistEntryPoint]");
//		try {
//			NeoComMSConnector.getSingleton().startChrono();
//			// If we receive a force command we should clear data before executing the request.
//			if ( force != null ) if ( force.equalsIgnoreCase("true") ) DataManagementModelStore.getSingleton().clearLoginList();
//			return AppModelStore.getSingleton().accessLoginList();
//		} catch (RuntimeException rtex) {
//			return new Hashtable<String, Login>();
//		} finally {
//			logger.info("<< [LoginController.loginlistEntryPoint]>[TIMING] Processing Time: - "
//					+ NeoComMSConnector.getSingleton().timeLapse());
//		}
//	}

    // TODO Code commented out until reactivation of functionality required.
//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/credentials", method = RequestMethod.GET, produces = "application/json")
//	public String credentialsEntryPoint( @RequestParam(value = "force", required = false) final String force ) {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/credentials");
//		logger.info(">> [LoginController.credentialsEntryPoint]");
//		final Chrono totalElapsed = new Chrono();
//		try {
//			// If we receive a force command we should clear data before executing the request.
//			if (force != null) if (force.equalsIgnoreCase("true")) DataManagementModelStore.getSingleton().cleanModel();
//			final List<Credential> credentials = DataManagementModelStore.accessCredentialList();
//			// Serialize the credentials as the Angular UI requires.
//			return serializeCredentialList(credentials);
//		} catch (RuntimeException rtex) {
//			return new JsonExceptionInstance(rtex.getMessage()).toJson();
//		} finally {
//			logger.info("<< [LoginController.credentialsEntryPoint]> [TIMING] Processing Time: {}", totalElapsed.printElapsed(Chrono.ChronoOptions.SHOWMILLIS));
//		}
//	}

//	@CrossOrigin()
//	@RequestMapping(value = "/api/v1/getauthorizationurl", method = RequestMethod.GET, produces = "application/json")
//	public String getAuthorizationURLRequestEntryPoint() {
//		logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: /api/v1/getauthorizationurl");
//		logger.info(">> [LoginController.getAuthorizationURLRequestEntryPoint]");
//		final Chrono totalElapsed = new Chrono();
//		try {
//			final NeoComOAuth20 service = new NeoComOAuth20(GlobalDataManager.getResourceString("R.esi.authorization.clientid")
//					, GlobalDataManager.getResourceString("R.esi.authorization.secretkey")
//					, GlobalDataManager.getResourceString("R.esi.authorization.callback")
//					, GlobalDataManager.getResourceString("R.esi.authorization.agent")
//					, NeoComOAuth20.ESIStore.DEFAULT
//					, ESINetworkManager.constructScopes());
//			return service.getAuthorizationUrl();
//		} catch (RuntimeException rtex) {
//			return new JsonExceptionInstance(rtex.getMessage()).toJson();
//		} finally {
//			logger.info("<< [LoginController.getAuthorizationURLRequestEntryPoint]> [TIMING] Processing Time: {}", totalElapsed.printElapsed(Chrono.ChronoOptions.SHOWMILLIS));
//		}
//	}

    @CrossOrigin()
    @RequestMapping(value = "/api/v1/exchangeauthorization/{code}/datasource/{datasource}"
            , method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<LoginResponse> exchangeAuthorizationEntryPoint(@PathVariable final String code) {
        logger.info(">>>>>>>>>>>>>>>>>>>>NEW REQUEST: " + "/api/v1/exchangeauthorization/{}", code);
        logger.info(">> [LoginController.exchangeAuthorizationEntryPoint]");
//		final Chrono totalElapsed = new Chrono();
        try {
            // With the 'code' complete the authorization flow and generate a new session.
            final LoginResponse loginResponse = exchangeAuthorization(code);
            // Convert any object instance returned to a Json serialized string.
//			return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(session);
            return new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
//		} catch ( JsonProcessingException jpe ) {
//			return new JsonExceptionInstance(jpe.getMessage()).toJson();
        } catch (NeoComException neoe) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("xApp-Error-Message", neoe.getMessage());
//			final ResponseEntity<LoginResponse> response = new ResponseEntity<LoginResponse>(HttpStatus.UNAUTHORIZED);

            logger.info("EX [LoginController.exchangeAuthorizationEntryPoint]> Exception: {}", neoe.getMessage());
            return new ResponseEntity<LoginResponse>(null, headers, HttpStatus.UNAUTHORIZED);

//			response.
//					ResponseEntity.
//			try {
//				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException(neoe.getMessage()));
//			} catch ( JsonProcessingException jpe ) {
//				return new JsonExceptionInstance(jpe.getMessage() + '\n' + neoe.getMessage()).toJson();
//			}
        } catch (IOException ioe) {
            return new ResponseEntity<LoginResponse>(HttpStatus.UNAUTHORIZED);
//			try {
//				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException(ioe.getMessage()));
//			} catch ( JsonProcessingException jpe ) {
//				return new JsonExceptionInstance(jpe.getMessage() + '\n' + ioe.getMessage()).toJson();
//			}
//		} catch ( NoSuchAlgorithmException nsae ) {
//			try {
//				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException(nsae.getMessage()));
//			} catch ( JsonProcessingException jpe ) {
//				return new JsonExceptionInstance(jpe.getMessage() + '\n' + nsae.getMessage()).toJson();
//			}
//		} catch ( NoSuchPaddingException nspe ) {
//			try {
//				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException(nspe.getMessage()));
//			} catch ( JsonProcessingException jpe ) {
//				return new JsonExceptionInstance(jpe.getMessage() + '\n' + nspe.getMessage()).toJson();
//			}
//		} catch ( BadPaddingException bpe ) {
//			try {
//				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException(bpe.getMessage()));
//			} catch ( JsonProcessingException jpe ) {
//				return new JsonExceptionInstance(jpe.getMessage() + '\n' + bpe.getMessage()).toJson();
//			}
//		} catch ( IllegalBlockSizeException ibse ) {
//			try {
//				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException(ibse.getMessage()));
//			} catch ( JsonProcessingException jpe ) {
//				return new JsonExceptionInstance(jpe.getMessage() + '\n' + ibse.getMessage()).toJson();
//			}
//		} catch ( InvalidKeyException ike ) {
//			try {
//				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException(ike.getMessage()));
//			} catch ( JsonProcessingException jpe ) {
//				return new JsonExceptionInstance(jpe.getMessage() + '\n' + ike.getMessage()).toJson();
//			}
//		} catch ( InvalidKeySpecException ikse ) {
//			try {
//				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException(ikse.getMessage()));
//			} catch ( JsonProcessingException jpe ) {
//				return new JsonExceptionInstance(jpe.getMessage() + '\n' + ikse.getMessage()).toJson();
//			}
//		} catch ( RuntimeException rte ) {
//			try {
//				return NeoComMicroServiceApplication.jsonMapper.writeValueAsString(new NeoComException(rte.getMessage()));
//			} catch ( JsonProcessingException jpe ) {
//				return new JsonExceptionInstance(jpe.getMessage() + '\n' + rte.getMessage()).toJson();
//			}
        } finally {
//			logger.info("<< [LoginController.exchangeAuthorizationEntryPoint]> [TIMING] Processing Time: {}"
//					, totalElapsed.printElapsed(Chrono.ChronoOptions.SHOWMILLIS));
        }
    }

    private LoginResponse exchangeAuthorization(final String authCode/*, final String publickey*/) throws NeoComException,
            IOException /*, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
			InvalidKeySpecException, InvalidKeyException */ {
        // Create the conversion call by coding.
        logger.info("-- [LoginController.exchangeAuthorization]> Preparing Verification HTTP request.");
        logger.info("-- [LoginController.exchangeAuthorization]> Creating access token request.");
        try {
            // Create a Retrofit request service to encapsulate the call.
            final GetAccessToken serviceGetAccessToken = new Retrofit.Builder()
                    .baseUrl(GlobalDataManager.getResourceString("R.esi.authorization.authorizationserver"))
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()
                    .create(GetAccessToken.class);
            logger.info("-- [LoginController.exchangeAuthorization]> Creating request body.");
            final TokenRequestBody tokenRequestBody = new TokenRequestBody()
                    .setCode(authCode);
            logger.info("-- [LoginController.exchangeAuthorization]> Creating request call.");
            final String peckString = GlobalDataManager.getResourceString("R.esi.authorization.clientid")
                    + ":"
                    + GlobalDataManager.getResourceString("R.esi.authorization.secretkey");
            final String peck = Base64.getEncoder().encodeToString(peckString.getBytes());
            final Call<TokenTranslationResponse> request = serviceGetAccessToken.getAccessToken("Basic " + peck
                    , GlobalDataManager.getResourceString("R.esi.authorization.contenttype")
                    , tokenRequestBody);
            // Getting the request response to be stored if valid.
            final Response<TokenTranslationResponse> response = request.execute();
            if (response.isSuccessful()) {
                logger.info("-- [LoginController.exchangeAuthorization]> Response is 200 OK.");
                final TokenTranslationResponse token = response.body();
                // Create a security verification instance.
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
                // Verify the character authenticated and create the Credential.
                logger.info("-- [LoginController.exchangeAuthorization]> Creating character verification.");
                final VerifyCharacter verificationService = new Retrofit.Builder()
                        .baseUrl(GlobalDataManager.getResourceString("R.esi.authorization.authorizationserver"))
                        .addConverterFactory(JacksonConverterFactory.create())
                        .client(verifyClient.build())
                        .build()
                        .create(VerifyCharacter.class);
                final String accessToken = token.getAccessToken();
                final Response<VerifyCharacterResponse> verificationResponse = verificationService.getVerification("Bearer " + accessToken).execute();
//				if ( verificationResponse.isSuccessful() ) {
//					logger.info("-- [LoginController.exchangeAuthorization]> Character verification OK.");
//
////					logger.info("-- [LoginController.checkCredencial]> Password matches.");
//					// Create a new session and store the authorization token and the rest of the data.
//					// Generate token.
//					final int newAccountIdentifier = Long.valueOf(verificationResponse.body().getCharacterID()).intValue();
//					final String salt = BCrypt.gensalt(8);
//					final String payload = DateTime.now().toString() + ":"
//							+ newAccountIdentifier + ":"
//							+ verificationResponse.body().getCharacterName();
//					final String authorizationToken = BCrypt.hashpw(payload, salt);
//					// Build up the session along with the credential data.
//					final SessionManager.AppSession session = new SessionManager.AppSession()
//							.setAuthorizationToken(authorizationToken)
//							.setPayload(payload)
//							.setAuthorizationPassword(salt)
//							.setUserIdentifier(newAccountIdentifier)
//							.setUserName(verificationResponse.body().getCharacterName());
////							.setCredential ( credential )
////							.setRole(credencial.getRole())
////							.store();
//					logger.info("-- [LoginController.checkCredencial]> Session id: {}", session.getId());
//
//					// Construct the login response that it is a structure with the token and the Pilot credential.
//					final Credential credential = new Credential(newAccountIdentifier)
//							.setAccountName(verificationResponse.body().getCharacterName())
//							.setAccessToken(token.getAccessToken())
//							.setTokenType(token.getTokenType())
//							.setExpires(Instant.now().plus(TimeUnit.SECONDS.toMillis(token.getExpires())).getMillis())
//							.setRefreshToken(token.getRefreshToken())
//							.setDataSource(GlobalDataManager.TRANQUILITY_DATASOURCE)
//							.setScope(ESINetworkManager.getStringScopes())
//							.store();
//					session.setCredential(credential)
//							.store();
//
//					final PilotV2 pilotData = InfinityGlobalDataManager.requestPilotV2(credential);
////					session.setPilotPublicData(pilotData);
//					// Create the response with the session token to conenct back the session on next calls.
//					final LoginResponse loginResponse = new LoginResponse()
//							.setAuthorizationToken(authorizationToken)
//							.setPilotPublicData(pilotData);
//					return loginResponse;
//
//
//					// TODO Create a new session store to keep the session data.
//					// Create the credential and store it on a new encrypted session.
////					NeoComMicroServiceApplication.sessionStore.put(session.getSessionId(), session);
////					// TODO - Store an additional copy of the session under a predefined session identifier.
////					NeoComMicroServiceApplication.sessionStore.put("-MOCK-SESSION-LOCATOR-", session);
//
//					// Clean up the list of credential to force a reload on next access.
//					// TODO This is a new code flow to store credentials only on the session and this on the database.
//
////					DataManagementModelStore.getSingleton().cleanModel();
////					// Update the Pilot information.
////					InfinityGlobalDataManager.requestPilotV2(credential);
////					return session;
////					return new NeoComMicroServiceApplication.NeoComSessionIdentifier(session);
//				} else
//					throw new NeoComException("NEO [LoginController.exchangeAuthorization]> the VerifyCharacterResponse response is " +
//							"invalid. "
//							+ verificationResponse.message());
            } else
                throw new NeoComException("NEO [LoginController.exchangeAuthorization]> the TokenTranslationResponse response is " +
                        "invalid. "
                        + response.message());
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
        } finally {
            // All went perfect. signal the end of the process
            logger.info("<< [LoginController.exchangeAuthorization]");
        }
        return null;
    }

    private String exceptionSerialization(final Exception exc) {
        String serializedException = null;
        try {
            serializedException = this.jsonMapper.writeValueAsString(exc);
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
            contentsSerialized = this.jsonMapper.writeValueAsString(credentials);
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
}